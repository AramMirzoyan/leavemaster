package com.leave.master.leavemaster.containers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Constant {

    PASSWORD("password"),
    CLIENT_ID("leavemaster_client"),
    CLIENT_SECRET("lCPJgheEZLuIJJZUyXjSnSr0HNmmSPkC");



    private final String value;

}
