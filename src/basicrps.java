



import java.beans.Expression;
import java.util.ArrayList;

public class basicrps<bot> implements Player{
    private final int r = 0;
    private final int p = 1;
    private final int s = 2;
    private Player bot;
    private Player user;

    public basicrps(Player user, Player bot) {
        this.user = user;
        this.bot = bot;
    }

    public boolean mainLogic(Player user,Player bot)

    @Override
    public boolean evaluate() {
        return false;
    }

    @Override
    public String emit() {
        return null;
    }
}
