
package com.fuelbuddy.data.exeption;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class ServiceException extends Exception {

  public ServiceException() {
    super();
  }

  public ServiceException(final Throwable cause) {
    super(cause);
  }
}
