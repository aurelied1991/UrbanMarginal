package outils.son;

import java.io.Serializable;

/**
 * Represente une duree
 */

public class Duree implements Serializable
{
  /**
   * Durée des sons en microsecondes
   */
  private long microsecondes;
  /**
   * Durée des sons en heure, minute, seconde, microseconde
   */
  private int heure, minute, seconde, microseconde;
  
  /**
   * Durée
   */
  public Duree()
  {
  }
  /**
   * Durée en microsecondes
   * @param microsecondes durée
   */
  public Duree(long microsecondes)
  {
    this.microsecondes = microsecondes;
    long temps = this.microsecondes;
    this.microseconde = (int)(temps % 1000000L);
    temps = temps / 1000000L;
    this.seconde = (int)(temps % 60L);
    temps = temps / 60L;
    this.minute = (int)(temps % 60L);
    temps = temps / 60L;
    this.heure = (int)temps;
  }
  
  /**
   * Durée en microsecondes
   * @param microseconde micro
   */
  public Duree(int microseconde)
  {
    if(microseconde < 0)
    {
      throw new IllegalArgumentException(
          "Le nombre de microsecondes ne peut pas être négative");
    }
    if(microseconde > 999999)
    {
      throw new IllegalArgumentException("Le nombre de microsecondes ne peut pas �tre plus de 999999, sinon on a des secondes");
    }
    this.microseconde = microseconde;
    this.microsecondes = (long)this.microseconde;
  }
  
  /**
   * Durée
   * @param seconde sec
   * @param microseconde ms
   */
  public Duree(int seconde, int microseconde)
  {
    this(microseconde);
    if(seconde < 0)
    {
      throw new IllegalArgumentException(
          "Le nombre de secondes ne peut pas être négative");
    }
    if(seconde > 59)
    {
      throw new IllegalArgumentException(
          "Le nombre de secondes ne peut pas être plus de 59, sinon on a des minutes");
    }
    this.seconde = seconde;
    this.microsecondes += 1000000L * (long)this.seconde;
  }
  
  /**
   * Durée
   * @param minute min
   * @param seconde sec
   * @param microseconde ms
   */
  public Duree(int minute, int seconde, int microseconde)
  {
    this(seconde, microseconde);
    if(minute < 0)
    {
      throw new IllegalArgumentException(
          "Le nombre de minutes ne peut pas être négative");
    }
    if(minute > 59)
    {
      throw new IllegalArgumentException(
          "Le nombre minutes ne peut pas être plus de 59, sinon on a des heures");
    }
    this.minute = minute;
    this.microsecondes += 60L * 1000000L * (long)this.minute;
  }
  
  /**
   * Durée
   * @param heure h
   * @param minute min
   * @param seconde sec
   * @param microseconde ms
   */
  public Duree(int heure, int minute, int seconde, int microseconde)
  {
    this(minute, seconde, microseconde);
    if(heure < 0)
    {
      throw new IllegalArgumentException(
          "Le nombre d'heure ne peut pas être négative");
    }
    this.heure = heure;
    this.microsecondes += 60L * 60L * 1000000L * (long)this.heure;
  }
  
  /**
   * Durée totale
   * @param duree duree
   */
  public Duree(Duree duree)
  {
    this.microsecondes = duree.microsecondes;
    this.microseconde = duree.microseconde;
    this.seconde = duree.seconde;
    this.minute = duree.minute;
    this.heure = duree.heure;
  }
  /**
   * Durée en microsecondes
   * @return microsecondes
   */
  public long enMicrosecondes()
  {
    return this.microsecondes;
  }
  
  /**
   * Durée en millisecondes
   * @return microsecodes / 1000L
   */
  public long enMillisecondes()
  {
    return this.microsecondes / 1000L;
  }
  
  /**
   * getter sur Microsecondes
   * @return microseconde
   */
  public int getMicroseconde()
  {
    return this.microseconde;
  }
  
  /**
   * getter sur getMilliseconde
   * @return millisecondes
   */
  public int getMilliseconde()
  {
    return this.microseconde / 1000;
  }
  
  /**
   * getter sur seconde
   * @return seconde
   */
  public int getSeconde()
  {
    return this.seconde;
  }
  
  /**
   * getter sur minute
   * @return minute
   */
  public int getMinute()
  {
    return this.minute;
  }
  
  /**
   * getter sur heure
   * @return heure
   */
  public int getHeure()
  {
    return this.heure;
  }
}
