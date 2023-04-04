package com.protify.Protify;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private int page;
    private int size;

    public Request(){
        this.page = 0;
        this.size = 20;
    }
}
