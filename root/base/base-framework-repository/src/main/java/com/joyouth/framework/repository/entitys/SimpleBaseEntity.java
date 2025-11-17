package com.joyouth.framework.repository.entitys;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @PACKAGE_NAME: com.joyouth.framework.repository.entitys
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/20
 * @TIME: 15:40
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class SimpleBaseEntity<ID extends Serializable> extends DefaultBaseEntity<ID> {
    private Boolean enable = true;
    private Boolean visible = true;
    private Boolean deleted = false;
    private String creator;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String modifier;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;
    private String deleter;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteTime;
    private String remark;
}

