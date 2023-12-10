package com.mintyn.mintyncard.controller;

import com.isdservice.qrcpay.dto.request.BankTransferRequest;
import com.isdservice.qrcpay.dto.request.QRCodeRequest;
import com.isdservice.qrcpay.dto.response.BankTransferResponse;
import com.isdservice.qrcpay.dto.response.QRcodeResponse;
import com.isdservice.qrcpay.entity.Wallet;
import com.isdservice.qrcpay.service.TransferService;
import com.isdservice.qrcpay.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final WalletService walletService;
    private final TransferService transferService;

    @GetMapping("/wallet-details")
    public ResponseEntity<Wallet> getWallet(){
        return walletService.getWallet();
    }
    @PostMapping("/verify-qrcode")
    public ResponseEntity<QRcodeResponse> verifyQRcode(@RequestBody QRCodeRequest request){
        return transferService.verifyQRCodeDetails(request);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankTransferResponse> bankTransfer(@RequestBody BankTransferRequest request){
        return transferService.bankTransfer(request);
    }
}

