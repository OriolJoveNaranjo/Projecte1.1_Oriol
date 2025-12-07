package dam2.grup2.entities;

/**
 *
 * @author panie
 */
 /** Representa una ficha colocada por un jugador en el tablero.
 * Una vez colocada, la ficha no puede moverse.
 */
public class Token {

    /** Jugador propietario de la ficha */
    private final Player owner;

    /**
     * Crea una ficha asociada a un jugador.
     * @param owner jugador propietario de la ficha
     */
    public Token(Player owner) {
        this.owner = owner;
    }

    /** @return jugador propietario de la ficha */
    public Player getOwner() {
        return owner;
    }
}
