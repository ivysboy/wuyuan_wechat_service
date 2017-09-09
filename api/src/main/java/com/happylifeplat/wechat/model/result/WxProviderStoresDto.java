package com.happylifeplat.wechat.model.result;

import com.happylifeplat.stores.service.domain.Stores;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xuwuyuan on 2017/9/7.
 */
public class WxProviderStoresDto implements Serializable {

    private static final long serialVersionUID = -3513525271270750452L;

    private String id;
    private String providerName;
    private List<Stores> stores;

    public List<Stores> getStores() {
        return stores;
    }

    public void setStores(List<Stores> stores) {
        this.stores = stores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

}
