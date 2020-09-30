package smucclaw.rps;

import java.util.ArrayList;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class App 
{
    public static void main( final String[] args)
    {
        // From the kie services, a container is created from the classpath
        KieServices ks = KieServices.get();
        KieContainer kc = ks.getKieClasspathContainer();

        KieSession ksession = kc.newKieSession("RPSKS");

        // The application can insert facts into the session

        // Let's start by creating the possible signs
        Sign rock = new Sign();
        rock.setName("Rock");
        Sign paper = new Sign();
        paper.setName("Paper");
        Sign scissors = new Sign();
        scissors.setName("Scissors");
        rock.setBeats(scissors);
        paper.setBeats(rock);
        scissors.setBeats(paper);

        // Let's create two players
        Player bob = new Player();
        bob.setName("Bob");
        bob.setPlay(rock);
        Player jane = new Player();
        jane.setName("Jane");
        jane.setPlay(paper);

        // Create a game object
        Game g = new Game();
        g.getPlayers().add(bob);
        g.getPlayers().add(jane);

        // Insert the game into the database.
        ksession.insert( g );

        // and fire the rules
        ksession.fireAllRules();

        // and then dispose the session
        ksession.dispose();
    }

    public static class Game
    {
        private Player  winner;
        private ArrayList<Player> players;

        public Game() {
            players = new ArrayList<Player>();
        }

        public ArrayList<Player> getPlayers() {
            return this.players;
        }

        public void setPlayers(ArrayList<Player> players) {
            this.players = players;
        }

        public Player getWinner() {
            return this.winner;
        }

        public void setWinner(Player winner) {
            this.winner = winner;
        }
    }

    public static class  Player
    {
        private String  name;
        private Sign    play;

        public Player() {}

        public String getName() {
            return this.name;
        }

        public Sign getPlay() {
            return this.play;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPlay(Sign play) {
            this.play = play;
        }

    }

    public static class Sign
    {
        private Sign    beats;
        private String  name;

        public Sign() {}

        public String getName() {
            return this.name;
        }

        public Sign getBeats() {
            return this.beats;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBeats(Sign other_sign) {
            this.beats = other_sign;
        }

    }
}

