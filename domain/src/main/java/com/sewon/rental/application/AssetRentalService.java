package com.sewon.rental.application;

import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_CHECK_EXPIRATION_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_REQUEST_APPROVE_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_REQUEST_RECEIVED_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RENTAL_REQUEST_REJECT_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RETURN_REQUEST_APPROVE_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RETURN_REQUEST_RECEIVED_KEY;
import static com.sewon.notification.constant.MessageRoutingKey.RETURN_REQUEST_REJECT_KEY;
import static com.sewon.rental.constant.RentalStatus.RENT;
import static com.sewon.rental.constant.RentalStatus.REQUEST_RENTAL;
import static com.sewon.rental.constant.RentalStatus.REQUEST_RETURN;
import static com.sewon.rental.exception.RentalErrorCode.RENTAL_NOT_FOUND;
import static java.time.LocalDateTime.now;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.asset.application.AssetService;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.common.exception.DomainException;
import com.sewon.inbound.constant.InboundType;
import com.sewon.inbound.model.AssetInbound;
import com.sewon.notification.application.NotificationProducer;
import com.sewon.outbound.constant.OutboundType;
import com.sewon.outbound.model.AssetOutbound;
import com.sewon.rental.constant.RentalStatus;
import com.sewon.rental.dto.AssetRentalResult;
import com.sewon.rental.model.AssetRental;
import com.sewon.rental.repository.AssetRentalRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AssetRentalService {

    private final AssetRentalRepository assetRentalRepository;
    private final AccountService accountService;
    private final AssetService assetService;
    private final AssetLocationService assetLocationService;

    private final NotificationProducer rentalProducer;

    public AssetRentalService(AssetRentalRepository assetRentalRepository,
        AccountService accountService, AssetService assetService,
        AssetLocationService assetLocationService,
        @Qualifier("rentalProducer") NotificationProducer rentalProducer) {
        this.assetRentalRepository = assetRentalRepository;
        this.accountService = accountService;
        this.assetService = assetService;
        this.assetLocationService = assetLocationService;
        this.rentalProducer = rentalProducer;
    }

    @Transactional
    public void requestAssetRental(Long assetId, Long locationId, LocalDate fromDate,
        LocalDate toDate, Long accountId) {
        Account account = accountService.findAccountById(accountId);
        Asset asset = assetService.findAssetById(assetId);
        AssetLocation toLocation = assetLocationService.findAssetLocationById(locationId);

        assetRentalRepository.save(
            getRequestAssetRental(fromDate, toDate, account, asset, toLocation));

        rentalProducer.sendingNotification(
            asset.getAffiliationId(),
            RENTAL_REQUEST_RECEIVED_KEY.getKey());
    }


    @Transactional
    public void approveAssetRental(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(rental -> {
                rental.inbounded(getRentalInbound(rental));
                rental.outbounded(getRentalOutbound(rental));
                rental.rentalApprove();
                rentalProducer.sendingNotification(
                    rental.getAccountAffiliationId(),
                    RENTAL_REQUEST_APPROVE_KEY.getKey()
                );
            });
    }

    @Transactional
    public void requestAssetReturn(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(rental -> {
                rental.requestRentalReturn();
                rentalProducer.sendingNotification(
                    rental.getLocationAffiliationId(),
                    RETURN_REQUEST_RECEIVED_KEY.getKey());
            });
    }

    @Transactional
    public void approveAssetReturn(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(rental -> {
                rental.outbounded(getReturnOutbound(rental));
                rental.inbounded(getReturnInbound(rental));
                rental.returnApprove();
                rentalProducer.sendingNotification(
                    rental.getAccountAffiliationId(),
                    RETURN_REQUEST_APPROVE_KEY.getKey()
                );
            });
    }

    @Transactional
    public void returnRequestCancel(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(AssetRental::returnRequestCancel);
    }

    @Transactional
    public void rejectReturnRequest(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(rental -> {
                rental.returnRequestCancel();
                rentalProducer.sendingNotification(
                    rental.getAccountAffiliationId(),
                    RETURN_REQUEST_REJECT_KEY.getKey()
                );
            });
    }

    @Transactional
    public void deleteAllAssetRentalById(List<Long> ids) {
        assetRentalRepository.deleteAllByIds(ids);
    }

    @Transactional
    public void rejectAllAssetRentalById(List<Long> ids) {
        assetRentalRepository.deleteAllByIds(ids);

        assetRentalRepository.findAllByIds(ids)
            .forEach(rental ->
                rentalProducer.sendingNotification(
                    rental.getAccountAffiliationId(),
                    RENTAL_REQUEST_REJECT_KEY.getKey()
                )
            );
    }

    public AssetRental findAssetRentalById(Long id) {
        return assetRentalRepository.findById(id)
            .orElseThrow(() -> new DomainException(RENTAL_NOT_FOUND));
    }

    public List<AssetRental> findAllAssetRental() {
        return assetRentalRepository.findAll();
    }

    // 다른 부서에서 대여 요청온 자산
    public List<AssetRentalResult> findAllRequestingAssetRentalByAssetAffiliation(
        Long affiliationId) {
        return assetRentalRepository.findAllByRentalStatusAndAssetAffiliation(
                REQUEST_RENTAL, affiliationId).stream()
            .map(AssetRentalResult::from)
            .toList();
    }

    // 우리 부서가 대여 요청한 자산
    public List<AssetRentalResult> findAllRequestingAssetRentalMyAffiliation(Long affiliationId) {
        return assetRentalRepository.findAllByRentalStatusAndMyAffiliation(
                REQUEST_RENTAL, affiliationId).stream()
            .map(AssetRentalResult::from)
            .toList();
    }

    // 우리 부서에서 대여 중인 자산
    @Transactional
    public List<AssetRentalResult> findAllAssetRentedMyAffiliation(Long affiliationId) {
        List<AssetRental> assetRentals = assetRentalRepository.findAllByRentalStatusAndMyAffiliation(
            RENT, affiliationId);
        assetRentals.stream()
            .filter(AssetRental::isExpireAndChange)
            .forEach(rental -> {
                String message = rental.getAsset().getBarcodeValue() + "." + affiliationId;
                rentalProducer.sendingNotification(message,
                    RENTAL_CHECK_EXPIRATION_KEY.getKey());
            });

        return assetRentals.stream().map(AssetRentalResult::from)
            .toList();
    }

    // 우리 부서에서 반납 요청 중인 자산
    public List<AssetRentalResult> findAllAssetReturnRequestingMyAffiliation(Long affiliationId) {
        return assetRentalRepository.findAllByRentalStatusAndMyAffiliation(
                REQUEST_RETURN, affiliationId).stream()
            .map(AssetRentalResult::from)
            .toList();
    }

    // 다른 부서에서 반납 요청온 자산
    public List<AssetRentalResult> findAllAssetReturnRequestingByOtherAffiliation(
        Long affiliationId) {
        return assetRentalRepository.findAllByRentalStatusAndAssetAffiliation(
                REQUEST_RETURN, affiliationId).stream()
            .map(AssetRentalResult::from)
            .toList();
    }

    public List<AssetRental> findAllAssetRentedByAccountName(String username) {
        return assetRentalRepository.findAllByAccountNameAndRentalStatus(username,
            RENT);
    }

    public List<AssetRental> findAllAssetRentExpireByAccountName(String username) {
        return assetRentalRepository.findAllByAccountNameAndRentalStatus(username,
            RentalStatus.EXPIRE);
    }

    public List<AssetRental> findAllMyRequestingAssetRentalByAccountName(String username) {
        return assetRentalRepository.findAllByAccountNameAndRentalStatus(username,
            REQUEST_RENTAL);
    }

    private AssetRental getRequestAssetRental(LocalDate fromDate, LocalDate toDate, Account account,
        Asset asset, AssetLocation toLocation) {
        return AssetRental.request(account, asset, toLocation, REQUEST_RENTAL,
            LocalDate.now(), fromDate, toDate, null);
    }

    private AssetInbound getRentalInbound(AssetRental assetRental) {
        return AssetInbound.of(InboundType.RENTAL, now(), assetRental.getAccount(),
            assetRental.getAsset(), assetRental.getToLocation());
    }

    private AssetOutbound getRentalOutbound(AssetRental assetRental) {
        return AssetOutbound.of(OutboundType.RENTAL, now(), assetRental.getAccount(),
            assetRental.getAsset(), assetRental.getFromLocation());
    }

    private AssetInbound getReturnInbound(AssetRental assetRental) {
        return AssetInbound.of(InboundType.RETURN, now(), assetRental.getAccount(),
            assetRental.getAsset(), assetRental.getFromLocation());
    }

    private AssetOutbound getReturnOutbound(AssetRental assetRental) {
        return AssetOutbound.of(OutboundType.RETURN, now(), assetRental.getAccount(),
            assetRental.getAsset(), assetRental.getToLocation());
    }


}
