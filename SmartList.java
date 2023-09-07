public class SmartList  {
	SListNode listFirst; //pointer to absolute front
	SListNode listLast;
	int numItems; //length of number
	SListNode forward; //This is a pointer that moves around
	SListNode backward; 
	
	
	public SmartList() {
		listFirst = null;
		listLast = null;
		numItems = 0;
		forward = null;
		backward = null;
	}

	public void resetForward() {
		//find first item and set it to listFirst
		forward = listFirst;
	}

	public byte getNextItem() {
		// This iterates through each element starting from the front
		//this returns the current back pointer and THEN moves forwards
		byte now;
		if (forward.next==null) {
			now = forward.digit; //gets byte value of the pointer
			forward = listFirst;
		}
		else {
			now = forward.digit;
		forward = forward.next;
		}
		return now;
	}

	public void resetBackward() {
		//finds the last item in the list and sets a pointer to it
		backward = listLast;
	}

	public byte getPriorItem() {
		//goes back an item
		//this returns the current back pointer and THEN moves backwards
		byte back;
		if (backward==listFirst) {
			back = backward.digit;
			backward = listLast; //this is so it raps around
		}
		else {
		back = backward.digit;
		backward = backward.back;
		}
		return back;
	}

	public int lengthIs() {
		// Returns the length of the number (number of nodes)
		return numItems;
	}

	public void insertFront(byte item) {
		// This sets the first digit/node in the list 
		SListNode newnum = new SListNode (item);
		if (listFirst == null) {
			listFirst = newnum;
			listLast = newnum;
			numItems++;
		}
		else {
			newnum.next = listFirst;
			newnum.back = null;
			listFirst=newnum;
			listFirst.back = newnum; //connects current list with new front node
			numItems++;
			
		}
		
	}

	public void insertEnd(byte item) {
		SListNode newnum = new SListNode (item);
		if (listFirst == null) {
			listFirst = newnum;
			listLast = newnum;
			numItems++;
		}
		else {
			newnum.next = null;
			newnum.back = listLast; //adds onto end of list
			listLast.next=newnum; //makes sure there's a next node to go to
			listLast=newnum; //sets pointer to absolute back
			numItems++;
			
		}
	}

}

class SListNode{
	/*This list of joined nodes is what makes up the number as a whole (ex: 100 would be composed
	* of nodes 1, 0, 0). So to build the number, you would insert the nodes from either the front
	* or back until they're in the right order. From then, you can use the calculator to perform 
	* operations. */
	protected byte digit;
	protected SListNode next;
	protected SListNode back;
	
	SListNode (byte c){
		digit = c;
		next = null;
		back = null;
	}
	
}
