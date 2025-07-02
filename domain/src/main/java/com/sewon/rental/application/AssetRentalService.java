package com.sewon.rental.application;

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
import com.sewon.outbound.constant.OutboundType;
import com.sewon.outbound.model.AssetOutbound;
import com.sewon.rental.constant.RentalStatus;
import com.sewon.rental.dto.AssetRentalResult;
import com.sewon.rental.model.AssetRental;
import com.sewon.rental.repository.AssetRentalRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AssetRentalService {

    private final AssetRentalRepository assetRentalRepository;
    private final AccountService accountService;
    private final AssetService assetService;
    private final AssetLocationService assetLocationService;


    @Transactional
    public void requestAssetRental(Long assetId, Long locationId, LocalDate fromDate,
        LocalDate toDate, Long accountId) {
        Account account = accountService.findAccountById(accountId);
        Asset asset = assetService.findAssetById(assetId);
        AssetLocation toLocation = assetLocationService.findAssetLocationById(locationId);

        assetRentalRepository.save(
            getRequestAssetRental(fromDate, toDate, account, asset, toLocation));
    }


    @Transactional
    public void approveAssetRental(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(rental -> {
                rental.inbounded(getRentalInbound(rental));
                rental.outbounded(getRentalOutbound(rental));
                rental.rentalApprove();
            });
    }

    @Transactional
    public void requestAssetReturn(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(AssetRental::requestRentalReturn);
    }

    @Transactional
    public void approveAssetReturn(List<Long> ids) {
        assetRentalRepository.findAllByIds(ids)
            .forEach(rental -> {
                rental.outbounded(getReturnOutbound(rental));
                rental.inbounded(getReturnInbound(rental));
                rental.returnApprove();
            });
    }


    @Transactional
    public void deleteAllAssetRentalById(List<Long> ids) {
        assetRentalRepository.deleteAllByIds(ids);
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
            .filter(AssetRental::isExpire)
            .forEach(AssetRental::rentalExpire);

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
