package com.miget.hxb.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * session信息模型
 *
 * @author yangfan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDetails implements java.io.Serializable {

    private Long userId;// 用户ID
    private String mobile;
    private Date expirationDate;

}
