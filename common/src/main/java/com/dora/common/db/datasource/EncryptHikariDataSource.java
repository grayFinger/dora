package com.dora.common.db.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptHikariDataSource extends HikariDataSource {
    private static final Logger log = LoggerFactory.getLogger(EncryptHikariDataSource.class);
    private boolean encrypt = true;

    public void setPassword(String password) {
        String _password = password;
        if (this.encrypt) {
            try {
                _password = DataSourceEncrypt.decrypt(password);
            } catch (Exception var4) {
                log.error("数据库密码不对");
            }
        }

        super.setPassword(_password);
    }

    public EncryptHikariDataSource() {
    }

    public boolean isEncrypt() {
        return this.encrypt;
    }

    public void setEncrypt(final boolean encrypt) {
        this.encrypt = encrypt;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EncryptHikariDataSource)) {
            return false;
        } else {
            EncryptHikariDataSource other = (EncryptHikariDataSource)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                return this.isEncrypt() == other.isEncrypt();
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EncryptHikariDataSource;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isEncrypt() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "EncryptHikariDataSource(encrypt=" + this.isEncrypt() + ")";
    }
}
