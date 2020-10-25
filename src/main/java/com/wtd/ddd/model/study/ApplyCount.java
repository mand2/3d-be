package com.wtd.ddd.model.study;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

/**
 * Created By mand2 on 2020-10-26.
 * 지원자 수 관련
 */
public class ApplyCount {
    private int memberNumber;
    private int waitCount;
    private int acceptCount;
    private int denyCount;
    private int cancelCount;

    public ApplyCount(int memberNumber, int waitCount, int acceptCount, int denyCount, int cancelCount) {
        this.memberNumber = memberNumber;
        this.waitCount = waitCount;
        this.acceptCount = acceptCount;
        this.denyCount = denyCount;
        this.cancelCount = cancelCount;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public int getWaitCount() {
        return waitCount;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public int getDenyCount() {
        return denyCount;
    }

    public int getCancelCount() {
        return cancelCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("memberNumber", memberNumber)
                .append("waitCount", waitCount)
                .append("acceptCount", acceptCount)
                .append("denyCount", denyCount)
                .append("cancelCount", cancelCount)
                .toString();
    }

}
