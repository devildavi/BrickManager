package com.brickmanager.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.brickmanager.data.entity.SetEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SetDao_Impl implements SetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SetEntity> __insertionAdapterOfSetEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSetBuiltStatus;

  public SetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSetEntity = new EntityInsertionAdapter<SetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `inventory_sets` (`id`,`name`,`pieceCount`,`isBuilt`,`estimatedMarketValue`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SetEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindLong(3, entity.getPieceCount());
        final int _tmp = entity.isBuilt() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindDouble(5, entity.getEstimatedMarketValue());
      }
    };
    this.__preparedStmtOfUpdateSetBuiltStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE inventory_sets SET isBuilt = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSet(final SetEntity set, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSetEntity.insert(set);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSetBuiltStatus(final String setId, final boolean isBuilt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSetBuiltStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isBuilt ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, setId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSetBuiltStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SetEntity>> getSetInventory() {
    final String _sql = "SELECT * FROM inventory_sets";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inventory_sets"}, new Callable<List<SetEntity>>() {
      @Override
      @NonNull
      public List<SetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPieceCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pieceCount");
          final int _cursorIndexOfIsBuilt = CursorUtil.getColumnIndexOrThrow(_cursor, "isBuilt");
          final int _cursorIndexOfEstimatedMarketValue = CursorUtil.getColumnIndexOrThrow(_cursor, "estimatedMarketValue");
          final List<SetEntity> _result = new ArrayList<SetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SetEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final int _tmpPieceCount;
            _tmpPieceCount = _cursor.getInt(_cursorIndexOfPieceCount);
            final boolean _tmpIsBuilt;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBuilt);
            _tmpIsBuilt = _tmp != 0;
            final double _tmpEstimatedMarketValue;
            _tmpEstimatedMarketValue = _cursor.getDouble(_cursorIndexOfEstimatedMarketValue);
            _item = new SetEntity(_tmpId,_tmpName,_tmpPieceCount,_tmpIsBuilt,_tmpEstimatedMarketValue);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
