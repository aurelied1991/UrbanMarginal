package outils.son.exceptions;

import java.io.File;
import java.net.URL;
import java.io.Serializable;

/**
 * Exception levee pour indiquer que le son n'a pas ete trouve <br>
 */

public class SonIntrouvableException
    extends SonException implements Serializable
{
  /**
   * Constrtuit l'exception pour les fichiers
   * @param fichier Fichier non trouve
   */
  public SonIntrouvableException(File fichier)
  {
    super("Le fichier " + fichier.getAbsolutePath() + " est introuvable");
  }
  /**
   * Construit l'exception pour les URL
   * @param url URL non trouvee
   */
  public SonIntrouvableException(URL url)
  {
    super("L'URL : " + url.getFile() + " est introuvable");
  }
  /**
   * Construit l'exception pour les sons de la ressource
   * @param nom Nom de la ressource non trouvee
   */
  public SonIntrouvableException(String nom)
  {
    super("Le son : " + nom + " est introuvable");
  }
}
