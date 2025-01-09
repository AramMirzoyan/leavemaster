package com.leave.master.leavemaster.containers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constant {

    PASSWORD("password"),
    CLIENT_ID("leavemaster_client"),
    CLIENT_SECRET("client_secret");



    private final String value;

}
