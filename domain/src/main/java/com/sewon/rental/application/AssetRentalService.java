package com.sewon.rental.application;

import static java.time.LocalDateTime.now;

import com.sewon.account.application.AccountService;
import com.sewon.account.model.Account;
import com.sewon.asset.application.AssetService;
import com.sewon.asset.model.Asset;
import com.sewon.assetlocation.application.AssetLocationService;
import com.sewon.assetlocation.model.AssetLocation;
import com.sewon.inbound.constant.InboundType;
import com.sewon.inbound.model.AssetInbound;
import com.sewon.outbound.constant.OutboundType;
import com.sewon.outbound.model.AssetOutbound;
import com.sewon.rental.constant.RentalStatus;
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
    public void approveAssetRental(Long rentalId) {
        AssetRental assetRental = findAssetRentalById(rentalId);
        assetRental.inbounded(getInbound(assetRental));
        assetRental.outbounded(getOutbound(assetRental));
        assetRental.rentalApprove();
    }


    @Transactional
    public void deleteAssetRentalById(Long id) {
        assetRentalRepository.deleteById(id);
    }

    public AssetRental findAssetRentalById(Long id) {
        return assetRentalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("자산대여를 찾을 수 없습니다."));
    }

    public List<AssetRental> findAllAssetRental() {
        return assetRentalRepository.findAll();
    }


    public List<AssetRental> findAllAssetRentalRequestingByDepartment(String department) {
        return assetRentalRepository.findAllByRentalStatus(RentalStatus.REQUEST)
            .stream()
            .filter(rental -> rental.getAsset().getDepartment().equals(department))
            .toList();
    }

    public List<AssetRental> findAllAssetRentedByAccountName(String username) {
        return assetRentalRepository.findAllByAccountNameAndRentalStatus(username,
            RentalStatus.RENT);
    }

    public List<AssetRental> findAllAssetRentExpireByAccountName(String username) {
        return assetRentalRepository.findAllByAccountNameAndRentalStatus(username,
            RentalStatus.EXPIRE);
    }

    public List<AssetRental> findAllMyRequestingAssetRentalByAccountName(String username) {
        return assetRentalRepository.findAllByAccountNameAndRentalStatus(username,
            RentalStatus.REQUEST);
    }

    private AssetRental getRequestAssetRental(LocalDate fromDate, LocalDate toDate, Account account,
        Asset asset, AssetLocation toLocation) {
        return AssetRental.request(account, asset, toLocation, RentalStatus.REQUEST,
            LocalDate.now(), fromDate, toDate, null);
    }

    private AssetOutbound getOutbound(AssetRental assetRental) {
        return AssetOutbound.of(OutboundType.RENTAL, now(), assetRental.getAccount(),
            assetRental.getAsset(), assetRental.getFromLocation());
    }

    private AssetInbound getInbound(AssetRental assetRental) {
        return AssetInbound.of(InboundType.RENTAL, now(), assetRental.getAccount(),
            assetRental.getAsset(), assetRental.getToLocation());
    }


}
