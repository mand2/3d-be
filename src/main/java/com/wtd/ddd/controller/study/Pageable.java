package com.wtd.ddd.controller.study;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.Data;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created By mand2 on 2020-10-26.
 * interface로 구현해야 하지만 급하니까 ..
 */
@Data
public class Pageable {
    private Long offset = 0L;
    private Integer limit = 20;

    public Long offset() {
        validation();
        this.offset = offset * limit;
        return this.offset;
    }

    public int limit() {
        return this.limit;
    }

    private void validation() {
        if (offset == null) this.offset = 0L;
        if (limit == null) this.limit = 20;
    }

}
