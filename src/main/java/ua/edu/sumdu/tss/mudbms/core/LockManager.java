package ua.edu.sumdu.tss.mudbms.core;

import java.util.HashMap;
import java.util.LinkedList;

public class LockManager {

  private static final HashMap lock = new HashMap<String, LinkedList<DbLock>>();

  enum LockType {
      SHARED_LOCK, EXCLUSIVE_LOCK
  }

  static class DbLock {

      protected LockType type;
      protected int trx_id;

      public DbLock(LockType lockType, int trx_id){
          this.type = lockType;
          this.trx_id = trx_id;
      }
  }

  public static DbLock wantRead(Transaction trx, String key){
      var current_lock = new DbLock( LockType.SHARED_LOCK, trx.getId());
      var locks = lock.get(key);
      if (locks == null){
          locks = (new LinkedList<DbLock>()).add(current_lock);
          lock.put(key,locks);
          return current_lock;
      }
      if (locks.getLast().type != LockType.EXCLUSIVE_LOCK){
          locks.add(lock);
          return  current_lock;
      }
      //TODO wait
      return current_lock;
  }

  public static DbLock wantWrite(Transaction trx, String key){
      var lock = new DbLock(LockType.EXCLUSIVE_LOCK, trx.getId());
      return  lock;
  }
}
