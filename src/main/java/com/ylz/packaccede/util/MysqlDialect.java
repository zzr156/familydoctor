package com.ylz.packaccede.util;

/**
 * Created by asus on 2017/10/09.
 */
import org.hibernate.dialect.MySQL5InnoDBDialect;

import java.sql.Types;

public class MysqlDialect extends MySQL5InnoDBDialect {

    public MysqlDialect(){
        super();
        registerHibernateType(Types.NULL,"NULL");
    }
}
