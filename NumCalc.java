public class NumCalc {

	SmartList x;
	int sign;

	public NumCalc(String number) { //constructor
		x = new SmartList();
		sign = 1; // 0 is negative, 1 is positive
		for (int i = 0; i < number.length(); i++) {
			if (number.charAt(i) == '-') {
				sign = 0;
			} else if (number.charAt(i) == '+') {
				sign = 1;
			} else {
				x.insertEnd((byte)Character.digit(number.charAt(i), 10));
			}
		}
	}
	
	public NumCalc(NumCalc number) { //copy constructor
		x = new SmartList();
		sign = number.sign;
		x.resetForward();
		x.resetBackward();
		number.x.resetForward();
		number.x.resetBackward();
		for (int i = 0; i < number.x.lengthIs(); i++) {
				x.insertEnd(number.x.getNextItem());
		}
	}

	public NumCalc add(NumCalc input) {
		NumCalc y = new NumCalc (input);
		String sum = "";
		int temp;
		int carry = 0;
		NumCalc z = new NumCalc("");
		x.resetBackward();
		y.x.resetBackward();
		/*
		 * issue size difference carry over at the end(the front)
		 */
		// check/compare size of string
		int big = 0;
		if (y.x.lengthIs() < x.lengthIs()) {
			big = x.lengthIs();
		} else {
			big = y.x.lengthIs();
		}
		if (y.sign == this.sign) { // if signs are even then do addition deal with sign later
			if (checkValue(y) == 1) {//y is bigger
			sum = addHelp(y.x, x);}
			else {
				sum = addHelp(x, y.x);
			}

			z = new NumCalc(sum);
			z.sign = y.sign;
		} 
		else {// check value do subtraction with bigger one first
			//ex: -2 + 5 is done as the subtraction 5-2
			if (checkValue(y) == 1) {//y is bigger
				sum = subHelp(y.x, x);
				z = new NumCalc(sum);
				z.sign = y.sign;
			} else {//x is bigger
				sum = subHelp(x, y.x);
				z = new NumCalc(sum);
				z.sign = sign;}
		}

		return z;
	}


	public NumCalc subtract(NumCalc input) {
		NumCalc y = new NumCalc (input);
		String sum = "";
		int temp;
		int carry = 0;
		NumCalc z = new NumCalc("");
		x.resetBackward();
		y.x.resetBackward();
		int big = 0;
		if (y.x.lengthIs() < x.lengthIs()) {
			big = x.lengthIs();
		} else {
			big = y.x.lengthIs();
		}
		if (y.sign == this.sign && this.sign == 1) {//two pos nums
			if (checkValue(y) == 1) {//y is bigger,
				sum = subHelp(y.x, x);
				z = new NumCalc(sum);
				z.sign = 0;
			} else {//x is bigger, send x in first
				sum = subHelp(x, y.x);
				z = new NumCalc(sum);
				z.sign = 1;
			}

		} else if (y.sign == this.sign && this.sign == 0) {
			if (checkValue(y) == 1) {
				sum = subHelp(y.x, x);
				z = new NumCalc(sum);
				z.sign = 1;
			} else {
				sum = subHelp(x, y.x);
				z = new NumCalc(sum);
				z.sign = 0;
			}
		} else {
			// signed and unsigned
			if (checkValue(y) == 1) {//y is bigger
				sum = addHelp(y.x, x);
				z = new NumCalc(sum);
				z.sign = sign;
			} else {//x is bigger
				sum = addHelp(x, y.x);
				z = new NumCalc(sum);
				z.sign = sign;}
		}
		return z;
	}


	public void setNegative() {
		/* This isn't used in the project proper due to manually
		being added in, but this method is specificall designed to flip
		the sign*/
		if (sign == 0) {
			sign = 1;
		} else {
			sign = 0;
		}
	}

	@Override
	public String toString() {
		//This converts the number to a string value
		String answer = "";
		x.resetForward();
		if (sign == 0) {
			answer = "-";
		}
		if (x.lengthIs() == 1 && x.getNextItem() == 0) {
			answer = "0";
			return answer;
		}
		x.resetForward();
		for (int i = 0; i < x.lengthIs(); i++) {
			answer = answer + x.getNextItem();
		}

		return answer;
	}

	public int checkValue(NumCalc y) {
		//Format: x.checkValue(y)
		x.resetForward();
		y.x.resetForward();
		int test1=0;
		int test2=0;
		if (x.lengthIs() > y.x.lengthIs()) {
			return 0;// x is bigger
		} else if (y.x.lengthIs() > x.lengthIs()) {
			return 1;// y is bigger
		} else {
			for (int i = 0; i < x.lengthIs(); i++) {
				test1=x.getNextItem();
				test2=y.x.getNextItem();
				if (test1 > test2) {
					return 0;// x is bigger
				} 
				else if (test1 < test2) {
					return 1;// y is bigger
				}
			}
			return 0;
		}
	}

	public String subHelp(SmartList x, SmartList y) {
		//made these to make the subtraction code cleaner
		String sum = "";
		int temp;
		int carry = 0;
		x.resetBackward();
		y.resetBackward();
		for (int i = 0; i < x.lengthIs(); i++) {
			if (i >= x.lengthIs()) {
				temp = y.getPriorItem() - carry;
				carry = 0;
				if (temp < 0) {
					carry = 1;
					temp = temp + 10;
				}
			} else if (i >= y.lengthIs()) {
				temp = x.getPriorItem() - carry;
				carry = 0;
				if (temp < 0) {
					carry = 1;
					temp = temp + 10;
				}
			} else {
				temp = (x.getPriorItem() - carry) - y.getPriorItem();
				carry = 0;
				if (temp < 0) {
					carry = 1;
					temp = temp + 10;
				}
			}
			sum = temp + sum;
		}
		return remove0(sum);
	}

	public String addHelp(SmartList x, SmartList y) {
		String sum = "";
		int temp;
		int carry = 0;
		x.resetBackward();
		y.resetBackward();

		for (int i = 0; i < x.lengthIs(); i++) {
			if (i >= y.lengthIs()) {
				temp = x.getPriorItem();
				temp = temp + carry;
				sum = (temp % 10) + sum;
				carry = temp / 10;
			} else {
				temp = x.getPriorItem() + y.getPriorItem();
				temp = temp + carry;
				sum = (temp % 10) + sum;
				carry = temp / 10;
			}
		}
		if (carry > 0) {
			sum = carry + sum;
		}
		return remove0(sum);
	}
	
	public String remove0(String s) {
		String sub = "";
		int zerocounter = 0;
		if (s.equals("0") || s.equals("-0")) {
			sub = "0";
			return sub;
		}
		for (int i = 0; i<s.length(); i++) {
			if (s.charAt(i) != '0' && s.charAt(i) != '-') {
				sub = s.substring(i);
				zerocounter++;
				break;
			}
		}
		if (zerocounter == 0) {
			sub = "0";
			return sub;
		}
		return sub;
	}

}
