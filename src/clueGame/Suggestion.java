// Authors: Michael Crews and Jhon Malagon

package clueGame;


//Suggestion class each player calls on when making a suggestion
public class Suggestion {
	public char room;
	public String weapon, person;
	public Suggestion(char room, String weapon, String person) {
		this.room = room;
		this.weapon = weapon;
		this.person = person;
	}
	
	
	@Override
	public String toString() {
		return "Suggestion [room=" + room + ", weapon=" + weapon + ", person=" + person + "]";
	}
	
	
}
