/**
 * IController.java
 * JRE v1.8.0_45
 * 
 * Created by William Myers on Jun 30, 2015.
 * Copyright (c) 2015 William Myers. All Rights reserved.
 */
package servertester.controllers;

/**
 * The Interface IController.
 */
public interface IController {

  /**
   * Initialize.
   */
  void initialize();

  /**
   * Operation selected.
   */
  void operationSelected();

  /**
   * Execute operation.
   */
  void executeOperation();
}
