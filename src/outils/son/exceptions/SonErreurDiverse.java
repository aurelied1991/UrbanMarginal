package outils.son.exceptions;

import java.io.Serializable;

/**
 * Exception levee signale un manque de droit, un flux coupe  en cours de route, une erreur ... <br>
 */

public class SonErreurDiverse
    extends SonException implements Serializable
{
  /**
   * Construit l'exception
   * @param e Exception genere à la construction du son
   */
  public SonErreurDiverse(Exception e)
  {
    super("Une erreur s'est produite lors de l'analyse du son : " +
          e.getMessage());
  }
}
