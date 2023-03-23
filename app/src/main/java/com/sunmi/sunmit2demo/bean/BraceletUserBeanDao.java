package com.sunmi.sunmit2demo.bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sunmi.sunmit2demo.bean.blescan.BraceletUserBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BRACELET_USER_BEAN".
*/
public class BraceletUserBeanDao extends AbstractDao<BraceletUserBean, Long> {

    public static final String TABLENAME = "BRACELET_USER_BEAN";

    /**
     * Properties of entity BraceletUserBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Icon = new Property(0, int.class, "icon", false, "ICON");
        public final static Property Id = new Property(1, Long.class, "id", true, "_id");
        public final static Property Mac = new Property(2, String.class, "mac", false, "MAC");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property SingValue = new Property(4, int.class, "singValue", false, "SING_VALUE");
    }


    public BraceletUserBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BraceletUserBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BRACELET_USER_BEAN\" (" + //
                "\"ICON\" INTEGER NOT NULL ," + // 0: icon
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 1: id
                "\"MAC\" TEXT," + // 2: mac
                "\"NAME\" TEXT," + // 3: name
                "\"SING_VALUE\" INTEGER NOT NULL );"); // 4: singValue
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BRACELET_USER_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BraceletUserBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getIcon());
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(3, mac);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        stmt.bindLong(5, entity.getSingValue());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BraceletUserBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getIcon());
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(3, mac);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
        stmt.bindLong(5, entity.getSingValue());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1);
    }    

    @Override
    public BraceletUserBean readEntity(Cursor cursor, int offset) {
        BraceletUserBean entity = new BraceletUserBean( //
            cursor.getInt(offset + 0), // icon
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mac
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.getInt(offset + 4) // singValue
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BraceletUserBean entity, int offset) {
        entity.setIcon(cursor.getInt(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setMac(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSingValue(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(BraceletUserBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(BraceletUserBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(BraceletUserBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
