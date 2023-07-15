/*
 * This file is generated by jOOQ.
 */
package com.szuse.bkguidance.jooq.tables.daos;

import com.szuse.bkguidance.jooq.tables.User;
import com.szuse.bkguidance.jooq.tables.records.UserRecord;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

/** This class is generated by jOOQ. */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class UserDao
    extends DAOImpl<UserRecord, com.szuse.bkguidance.jooq.tables.pojos.User, Long> {

  /** Create a new UserDao without any configuration */
  public UserDao() {
    super(User.USER, com.szuse.bkguidance.jooq.tables.pojos.User.class);
  }

  /** Create a new UserDao with an attached configuration */
  public UserDao(Configuration configuration) {
    super(User.USER, com.szuse.bkguidance.jooq.tables.pojos.User.class, configuration);
  }

  @Override
  public Long getId(com.szuse.bkguidance.jooq.tables.pojos.User object) {
    return object.getId();
  }

  /**
   * Fetch records that have <code>id BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfId(
      Long lowerInclusive, Long upperInclusive) {
    return fetchRange(User.USER.ID, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>id IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchById(Long... values) {
    return fetch(User.USER.ID, values);
  }

  /** Fetch a unique record that has <code>id = value</code> */
  public com.szuse.bkguidance.jooq.tables.pojos.User fetchOneById(Long value) {
    return fetchOne(User.USER.ID, value);
  }

  /** Fetch a unique record that has <code>id = value</code> */
  public Optional<com.szuse.bkguidance.jooq.tables.pojos.User> fetchOptionalById(Long value) {
    return fetchOptional(User.USER.ID, value);
  }

  /**
   * Fetch records that have <code>email BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfEmail(
      String lowerInclusive, String upperInclusive) {
    return fetchRange(User.USER.EMAIL, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>email IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByEmail(String... values) {
    return fetch(User.USER.EMAIL, values);
  }

  /** Fetch a unique record that has <code>email = value</code> */
  public com.szuse.bkguidance.jooq.tables.pojos.User fetchOneByEmail(String value) {
    return fetchOne(User.USER.EMAIL, value);
  }

  /** Fetch a unique record that has <code>email = value</code> */
  public Optional<com.szuse.bkguidance.jooq.tables.pojos.User> fetchOptionalByEmail(String value) {
    return fetchOptional(User.USER.EMAIL, value);
  }

  /**
   * Fetch records that have <code>username BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfUsername(
      String lowerInclusive, String upperInclusive) {
    return fetchRange(User.USER.USERNAME, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>username IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByUsername(String... values) {
    return fetch(User.USER.USERNAME, values);
  }

  /**
   * Fetch records that have <code>account BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfAccount(
      String lowerInclusive, String upperInclusive) {
    return fetchRange(User.USER.ACCOUNT, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>account IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByAccount(String... values) {
    return fetch(User.USER.ACCOUNT, values);
  }

  /**
   * Fetch records that have <code>password BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfPassword(
      String lowerInclusive, String upperInclusive) {
    return fetchRange(User.USER.PASSWORD, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>password IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByPassword(String... values) {
    return fetch(User.USER.PASSWORD, values);
  }

  /**
   * Fetch records that have <code>avatar BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfAvatar(
      String lowerInclusive, String upperInclusive) {
    return fetchRange(User.USER.AVATAR, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>avatar IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByAvatar(String... values) {
    return fetch(User.USER.AVATAR, values);
  }

  /**
   * Fetch records that have <code>create_time BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfCreateTime(
      LocalDate lowerInclusive, LocalDate upperInclusive) {
    return fetchRange(User.USER.CREATE_TIME, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>create_time IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByCreateTime(LocalDate... values) {
    return fetch(User.USER.CREATE_TIME, values);
  }

  /**
   * Fetch records that have <code>update_time BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfUpdateTime(
      LocalDate lowerInclusive, LocalDate upperInclusive) {
    return fetchRange(User.USER.UPDATE_TIME, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>update_time IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByUpdateTime(LocalDate... values) {
    return fetch(User.USER.UPDATE_TIME, values);
  }

  /**
   * Fetch records that have <code>deleted BETWEEN lowerInclusive AND
   * upperInclusive</code>
   */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchRangeOfDeleted(
      Boolean lowerInclusive, Boolean upperInclusive) {
    return fetchRange(User.USER.DELETED, lowerInclusive, upperInclusive);
  }

  /** Fetch records that have <code>deleted IN (values)</code> */
  public List<com.szuse.bkguidance.jooq.tables.pojos.User> fetchByDeleted(Boolean... values) {
    return fetch(User.USER.DELETED, values);
  }
}