package com.memastick.backmem.main.constant;

import com.memastick.backmem.notification.constant.NotifyType;

public class LinkConstant {
    
    public static final String LINK_DNA = GlobalConstant.URL + "/home?event=" + NotifyType.DNA;

    public static final String LINK_MEMECOINS = GlobalConstant.URL + "/home?event=" + NotifyType.MEME_COIN;

    public static final String LINK_CREATING = GlobalConstant.URL + "/memes/create";

    public static final String LINK_ALLOWANCE = GlobalConstant.URL + "/modal?type=" + NotifyType.ALLOWANCE;

    public static final String LINK_MEME = GlobalConstant.URL + "/memes/share";

    public static final String LINK_ICON = GlobalConstant.URL + "/assets/images/title-logo.png";
}
