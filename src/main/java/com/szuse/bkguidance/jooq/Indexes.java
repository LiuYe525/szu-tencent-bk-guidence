/*
 * This file is generated by jOOQ.
 */
package com.szuse.bkguidance.jooq;

import com.szuse.bkguidance.jooq.tables.User;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

/** A class modelling indexes of tables in public. */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Indexes {

  // -------------------------------------------------------------------------
  // INDEX definitions
  // -------------------------------------------------------------------------

  public static final Index LOGIN_SEARCH_ACCOUNT_IDX =
      Internal.createIndex(
          DSL.name("login_search_account_idx"),
          User.USER,
          new OrderField[] {User.USER.ACCOUNT, User.USER.PASSWORD},
          false);
  public static final Index LOGIN_SEARCH_EMAIL_IDX =
      Internal.createIndex(
          DSL.name("login_search_email_idx"),
          User.USER,
          new OrderField[] {User.USER.EMAIL, User.USER.PASSWORD},
          false);
  public static final Index UNIQUE_EMAIL_IDX =
      Internal.createIndex(
          DSL.name("unique_email_idx"), User.USER, new OrderField[] {User.USER.EMAIL}, true);
}
