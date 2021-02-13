// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees.
    // No changes should be required in the A1DynamicMem functions.
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees.



    public void Defragment() {
        Dictionary freeList = this.freeBlk;
        //

        //
        Dictionary newFreeList;
        if(this.type == 1) return;
        if(this.type == 2) newFreeList = new BSTree();
        else newFreeList = new AVLTree();


        Dictionary e = this.freeBlk.getFirst();
        if (e==null) return;
        if(e.getNext() == null) return;
        while(e!=null){
          newFreeList.Insert(e.address, e.size, e.address);
          e = e.getNext();
        }

        Dictionary newFirst = newFreeList.getFirst();
        Dictionary tempNext = newFirst.getNext();
        while(tempNext!=null){
          if((newFirst.address+newFirst.size) == tempNext.address){
            int a1 = newFirst.address, b1 = newFirst.size;
            int a2 = tempNext.address, b2 = tempNext.size;
            newFreeList.Delete(newFirst);
            newFreeList.Delete(tempNext);
            tempNext = newFreeList.Insert(a1, b1 + b2, a1);
          }


          newFirst = tempNext;
          tempNext = tempNext.getNext();
        }
        //System.out.println(123456789);

        Dictionary new2FreeList;
        if(this.type == 2) new2FreeList = new BSTree();
        else new2FreeList = new AVLTree();

        Dictionary d = newFreeList.getFirst();

        while(d!=null){
          new2FreeList.Insert(d.address, d.size, d.size);
          d = d.getNext();
        }


        this.freeBlk = new2FreeList;
    }

    private void printBlk(){
      System.out.print("\nfreeBlk is : ");
      Dictionary first = this.freeBlk.getFirst();
      while(first!=null){
        System.out.print(" ("+first.address+", "+first.size+", "+first.key+") ");
        if(this.freeBlk==first) System.out.print(" <-- ");
        first = first.getNext();
      }

      System.out.print("\nallocBlk is : ");
      first = this.allocBlk.getFirst();
      while(first!=null){
        System.out.print(" ("+first.address+", "+first.size+", "+first.key+") ");
        if(this.allocBlk==first) System.out.print(" <-- ");
        first = first.getNext();
      }
      System.out.print("\n");
    }

    private void printBlk2(Dictionary freeBlk){
      Dictionary temp = freeBlk.getFirst();
      while(temp!=null){
        System.out.print("(" + temp.address + ", " + temp.size + ", " + temp.key+ ") ");
        temp = temp.getNext();
      }
      System.out.println();
    }

    public static void main(String[] args){
      A2DynamicMem mem = new A2DynamicMem(100, 2);
      /*mem.printBlk();
      System.out.println(mem.Allocate(5000));mem.printBlk();
      System.out.println(mem.Allocate(10000));mem.printBlk();
      System.out.println(mem.Allocate(15000));mem.printBlk();
      System.out.println(mem.Allocate(5000));mem.printBlk();
      System.out.println(mem.Free(5000));mem.printBlk();
      System.out.println(mem.Free(0)); mem.printBlk();
      mem.Defragment();mem.printBlk();
      System.out.println(mem.Free(30000)); mem.printBlk();
      System.out.println();
      */

      System.out.println(mem.Allocate(10));mem.printBlk();
      mem.Defragment();mem.printBlk();
      System.out.println(mem.Free(0));mem.printBlk();
      System.out.println(mem.Free(1));mem.printBlk();

      System.out.print("\n\n\n");
      mem = new A2DynamicMem(100, 2);
      System.out.println(mem.Allocate(10));mem.printBlk();
      System.out.println(mem.Free(0));mem.printBlk();
      mem.Defragment();mem.printBlk();
      System.out.println(mem.Allocate(10));mem.printBlk();
      System.out.println(mem.Free(0));mem.printBlk();
      System.out.println(mem.Free(1));mem.printBlk();
      mem.Defragment();mem.printBlk();
      System.out.println(mem.Allocate(10));mem.printBlk();

      System.out.print("\n\n\n");

      mem = new A2DynamicMem(20, 2);
      System.out.println(mem.Allocate(10));mem.printBlk();
      System.out.println(mem.Allocate(10));mem.printBlk();
      System.out.println(mem.Allocate(10));mem.printBlk();
      System.out.println(mem.Free(10));mem.printBlk();
      System.out.println(mem.Free(20));mem.printBlk();
      mem.Defragment();mem.printBlk();
      System.out.println(mem.Allocate(30));mem.printBlk();
      System.out.println(mem.Allocate(20));mem.printBlk();

      System.out.print("\n\n\n");

      mem = new A2DynamicMem(10000, 2);
      System.out.println(mem.Allocate(9000));mem.printBlk();
      System.out.println(mem.Free(0));mem.printBlk();
      System.out.println(mem.Allocate(20));mem.printBlk();
      System.out.println(mem.Allocate(40));mem.printBlk();
      System.out.println(mem.Allocate(60));mem.printBlk();
      System.out.println(mem.Free(20));mem.printBlk();
      System.out.println(mem.Free(60));mem.printBlk();
      System.out.println(mem.Allocate(1000));mem.printBlk();
      mem.Defragment();mem.printBlk();
      System.out.println(mem.Allocate(8000));mem.printBlk();
      System.out.println(mem.Free(1020));mem.printBlk();
      System.out.println(mem.Allocate(200));mem.printBlk();
      System.out.println(mem.Free(20));mem.printBlk();
      System.out.println(mem.Free(0));mem.printBlk();
      System.out.println(mem.Free(220));mem.printBlk();
      System.out.println(mem.Free(9120));mem.printBlk();
      //System.out.println("(" + t.address + ", " + t.size + ", " + t.key + ") ");
      mem.Defragment();mem.printBlk();
      //System.out.println(mem.Allocate(9000));mem.printBlk();
      //System.out.println(mem.Allocate(2500));mem.printBlk();







    }
}
