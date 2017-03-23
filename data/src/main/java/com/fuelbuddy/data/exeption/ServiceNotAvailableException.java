
package com.fuelbuddy.data.exeption;

/**
 * Exception throw by the application when a there is a network connection exception.
 */
public class ServiceNotAvailableException extends Exception {

  public ServiceNotAvailableException() {
    super();
  }

  public ServiceNotAvailableException(final Throwable cause) {
    super(cause);
  }
}
