package assignment3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DogShelter implements Iterable<Dog>{
    public DogNode root;

    public DogShelter(Dog d) {
        this.root = new DogNode(d);
    }

    private DogShelter(DogNode nodeDog) {
        this.root = nodeDog;
    }


    // add a dog to the shelter
    public void shelter(Dog d) {
        if (root == null)
            root = new DogNode(d);
        else
            root = root.shelter(d);
    }

    // removes the dog who has been at the shelter the longest
    public Dog adopt() {
        if (root == null)
            return null;

        Dog d = root.d;
        root =  root.adopt(d);
        return d;
    }

    // overload adopt to remove from the shelter a specific dog
    public void adopt(Dog d) {
        if (root != null)
            root = root.adopt(d);
    }


    // get the oldest dog in the shelter
    public Dog findOldest() {
        if (root == null)
            return null;

        return root.findOldest();
    }

    // get the youngest dog in the shelter
    public Dog findYoungest() {
        if (root == null)
            return null;

        return root.findYoungest();
    }

    // get dog with highest adoption priority with age within the range
    public Dog findDogToAdopt(int minAge, int maxAge) {
        return root.findDogToAdopt(minAge, maxAge);


    }

    // Returns the expected vet cost the shelter has to incur in the next numDays days
    public double budgetVetExpenses(int numDays) {
        if (root == null)
            return 0;

        return root.budgetVetExpenses(numDays);
    }

    // returns a list of list of Dogs. The dogs in the list at index 0 need to see the vet in the next week.
    // The dogs in the list at index i need to see the vet in i weeks.
    public ArrayList<ArrayList<Dog>> getVetSchedule() {
        if (root == null)
            return new ArrayList<ArrayList<Dog>>();

        return root.getVetSchedule();
    }


    public Iterator<Dog> iterator() {
        return new DogShelterIterator();
    }


    public class DogNode {
        public Dog d;
        public DogNode younger;
        public DogNode older;
        public DogNode parent;

        public DogNode(Dog d) {
            this.d = d;
            this.younger = null;
            this.older = null;
            this.parent = null;
        }

// START OF SHELTER CODE

        //BST add
        DogNode insertRec( Dog d1) {

            if (d1.getAge() > this.d.getAge()) {
                if (this.older == null) {
                    this.older = new DogNode(d1);
                    this.older.parent = this;
                    return this.older;
                }
                else {
                    return this.older.insertRec(d1);
                }

            }
            // do the same thing
            else if (d1.getAge() < this.d.getAge()) {
                if (this.younger == null) {
                    this.younger = new DogNode(d1);
                    this.younger.parent = this;
                    return this.younger;
                }
                else {
                    return this.younger.insertRec(d1);
                }
            }
            return null;
        }
        // returning the node you just added

        // --------------------------------------------------------

        DogNode leftChildUpheap (DogNode nodeD, DogNode nodeDParent) {


            nodeDParent.younger= nodeD.older;
            if (nodeD.older != null) {
                nodeD.older.parent = nodeDParent;
            }
            nodeD.older = nodeDParent;
            return nodeD;


        }

        // --------------------------------------------------------

        DogNode rightChildUpheap (DogNode nodeD, DogNode nodeDParent) {

            nodeDParent.older= nodeD.younger;

            if (nodeD.younger != null) {
                nodeD.younger.parent = nodeDParent;
            }
            nodeD.younger = nodeDParent;
            return nodeD;
        }

        // --------------------------------------------------------



        public DogNode shelter (Dog d) { //CHECK IF R ROOT

            // call BST add
            // returns the root, passes as input the root and the dog
            DogNode r = insertRec(d);

			/*
			if(r.parent == null) {
				return r;
			}
			*/
            DogNode p =r.parent;

            // r is the node just added


            // while (d.daysatshelter > his parent .daysatshelter) && parent != null
            while(( p != null) && (d.getDaysAtTheShelter() > p.d.getDaysAtTheShelter() ) ) {

                if(p.parent!= null) {
                    if(p.parent.younger == p){
                        p.parent.younger = r;
                    }
                    else {
                        p.parent.older = r;

                    }

                }

                r.parent = p.parent;
                p.parent = r;

                if(p.younger == r) {
                    r = leftChildUpheap (r, p);
                }


                else if (p.older == r){

                    r = rightChildUpheap (r, p);
                }


                p = r.parent;

            }

            if (p == null) {
                return r;
            }

            return this; // return the root of the tree
        }







        //-------------------------------------------------------------

        //START OF ADOPOT



        //function to find the node

        public DogNode searchNode(DogNode r, Dog d){
            // if key is not present in the key


            // if key is found
            if (r.d == d) {
                return r;
            }

            // if key is less than the root node, search in the left subtree
            else if (d.getAge() < r.d.getAge()) {
                return searchNode(r.younger, d);
            }

            // else search in the right subtree
            return searchNode(r.older, d);
        }


        // returns the DogNode that the dog is at











//------------------------------------------------------------------------





        public DogNode adopt(Dog d) {


            if(d.getAge() < this.d.getAge()) {
                this.younger = this.younger.adopt(d);
            }

            else if(d.getAge() > this.d.getAge()) {
                this.older = this.older.adopt(d);
            }

            else {

                // dog is a leaf
                if(this.younger == null && this.older == null) {

                    this.d = null;
                    return null;

                }


                //dog has one left child
                else if(this.younger != null && this.older == null) {
                    this.younger.parent = this.parent;
                    if (this.parent != null) {
                        if(this.parent.younger == this) {
                            this.parent.younger = this.younger;
                        }
                        else {
                            this.parent.older = this.younger;
                        }

                    }
                    return this.younger;
                }



                //dog has one right child
                else if(this.younger == null && this.older != null) {
                    this.older.parent = this.parent;

                    if (this.parent != null) {
                        if(this.parent.younger == this) {
                            this.parent.younger = this.older;
                        }
                        else {
                            this.parent.older = this.older;
                        }

                    }

                    return this.older;


                }


                //dog has 2 childs
                //ONLY CASE YOU HAVE TO DO UPHEAP
                else if(this.younger != null && this.older != null) {

                    DogNode nodeDog = this;
                    DogNode oldestNode;
                    oldestNode = nodeDog.younger.findOldestNode();
                    Dog fOld;
                    fOld = oldestNode.d;

                    nodeDog.d = fOld;

                    if (oldestNode.younger != null) {
                        oldestNode.parent.older = oldestNode.younger;
                        oldestNode.younger.parent = oldestNode.parent;
                    }
                    else {
                        oldestNode.parent.older = null;
                        oldestNode.parent = null;
                    }

                    while (((nodeDog.younger != null && nodeDog.younger.d.getDaysAtTheShelter() > nodeDog.d.getDaysAtTheShelter()) || ((nodeDog.older != null && nodeDog.older.d.getDaysAtTheShelter() > nodeDog.d.getDaysAtTheShelter())))) {


                        if (nodeDog.older == null && nodeDog.younger != null) {
                            rightRotation(nodeDog.younger, nodeDog);
                        }
                        else if (nodeDog.younger == null && nodeDog.older != null) {
                            leftRotation(nodeDog.older, nodeDog);
                        }
                        else {
                            if (nodeDog.younger.d.getDaysAtTheShelter() > nodeDog.older.d.getDaysAtTheShelter()) {
                                rightRotation(nodeDog.younger, nodeDog);
                            }
                            else {
                                leftRotation(nodeDog.older, nodeDog);
                            }
                        }


                    }
                    if (nodeDog.parent == null) {
                        return nodeDog;
                    }
                    else {
                        while (nodeDog.parent != null) {
                            nodeDog = nodeDog.parent;
                        }
                        return nodeDog;
                    }


                }

            }

            return null;

        }




        private DogNode findOldestNode() {
            DogNode current = this;
            while (current.older != null) {
                current = current.older;
            }
            return (current);
        }




        private DogNode rightRotation(DogNode node, DogNode bigNode) {

            if (bigNode != null) {
                if (node.older != null) {
                    node.older.parent = bigNode;
                    bigNode.younger = node.older;
                }
                else {
                    bigNode.younger = null;
                }
            }
            node.older = bigNode;
            if (bigNode.parent != null) {
                if (bigNode.parent.older == bigNode) {
                    bigNode.parent.older = node;
                }
                else  {
                    bigNode.parent.younger = node;
                }
            }
            node.parent = bigNode.parent;
            bigNode.parent = node;
            return node;
        }

        private DogNode leftRotation(DogNode node,  DogNode bigNode) {
            if (bigNode != null) {
                if (node.younger != null) {
                    node.younger.parent = bigNode;
                    bigNode.older = node.younger;
                }
                else {
                    bigNode.older = null;
                }
            }
            node.younger = bigNode;
            if (bigNode.parent != null) {
                if (bigNode.parent.older == bigNode) {
                    bigNode.parent.older = node;
                }
                else  {
                    bigNode.parent.younger = node;
                }
            }
            node.parent = bigNode.parent;
            bigNode.parent = node;

            return node;
        }





        public Dog findOldest() {
            DogNode current = this;
            while (current.older != null) {
                current = current.older;
            }
            return (current.d);
        }

        public Dog findYoungest() {
            DogNode current = this;
            while (current.younger != null) {
                current = current.younger;
            }
            return (current.d);

        }





        public Dog findDogToAdopt(int minAge, int maxAge) {
            if ((this.d.getAge() >= minAge) && (this.d.getAge() <= maxAge)) {
                return this.d;
            }
            else if ((this.d.getAge() > maxAge) && (this.younger != null)) {
                return this.younger.findDogToAdopt(minAge, maxAge);
            }
            else if ((this.d.getAge() < minAge) && (this.older != null)) {
                return this.older.findDogToAdopt(minAge, maxAge);
            }
            else {
                return null;
            }
        }


        public double budgetVetExpenses(int numDays) {
            double cost = 0.0;
            //traverse through the tree

            if (this.d.getDaysToNextVetAppointment() <= numDays) {
                cost += this.d.getExpectedVetCost();
            }
            if (this.younger != null) {
                cost += this.younger.budgetVetExpenses(numDays);
            }
            if (this.older != null) {
                cost += this.older.budgetVetExpenses(numDays);
            }


            return cost;
        }

        public ArrayList<ArrayList<Dog>> getVetSchedule() {

            // create a new array list
            ArrayList<ArrayList<Dog>> vetSchedule = new ArrayList<ArrayList<Dog>>();

            DogShelter dogs = new DogShelter(this);

            Iterator<Dog> iter1 = dogs.iterator();

            Dog d1 = iter1.next();

            while(iter1.hasNext()) {
                // if the index is on bound
                if(vetSchedule.size()-1 >= d1.getDaysToNextVetAppointment()/7) {
                    vetSchedule.get(d1.getDaysToNextVetAppointment()/7).add(d1);
                }
                //if there index is not on the array
                else {
                    //make a space into the array
                    for(int i = vetSchedule.size(); i <= d1.getDaysToNextVetAppointment()/7; i++) {
                        vetSchedule.add(new ArrayList<Dog>());
                    }
                    //add the dog
                    vetSchedule.get(d1.getDaysToNextVetAppointment()/7).add(d1);
                }
                d1 = iter1.next();
            }
            //make sure last dog is getting added
            if(vetSchedule.size()-1 >= d1.getDaysToNextVetAppointment()/7) {
                vetSchedule.get(d1.getDaysToNextVetAppointment()/7).add(d1);
            }
            else {
                for(int i = vetSchedule.size(); i <= d1.getDaysToNextVetAppointment()/7; i++) {
                    vetSchedule.add(new ArrayList<Dog>());
                }
                vetSchedule.get(d1.getDaysToNextVetAppointment()/7).add(d1);
            }

            return vetSchedule;
        }


        public String toString() {
            String result = this.d.toString() + "\n";
            if (this.younger != null) {
                result += "younger than " + this.d.toString() + " :\n";
                result += this.younger.toString();
            }
            if (this.older != null) {
                result += "older than " + this.d.toString() + " :\n";
                result += this.older.toString();
            }
			/*if (this.parent != null) {
				result += "parent of " + this.d.toString() + " :\n";
				result += this.parent.d.toString() +"\n";
			}*/
            return result;
        }

    }



    private class DogShelterIterator implements Iterator<Dog> {
        ArrayList<Dog> nodesSorted;
        int index;

        private DogShelterIterator() {
            this.nodesSorted = new ArrayList<Dog>();
            this.index = -1;
            this.inorder(root);
        }

        private void inorder(DogNode root) {
            if (root == null) {
                return;
            }

            this.inorder(root.younger);
            this.nodesSorted.add(root.d);
            this.inorder(root.older);

        }

        public Dog next(){
            if(!this.hasNext()) {
                throw new NoSuchElementException ("you reached the last element");
            }
            return this.nodesSorted.get(++this.index);
        }

        public boolean hasNext() {

            return this.index + 1 < this.nodesSorted.size();
        }

    }
}
