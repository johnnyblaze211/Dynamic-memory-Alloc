// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List temp = this.next;
        A1List newnode = new A1List(address, size, key);
        this.next = newnode;
        newnode.prev = this;
        newnode.next = temp;
        temp.prev = newnode;

        return newnode;
    }

    public boolean Delete(Dictionary d)
    {
        A1List rnode = this, lnode = this;
        if(this.prev==null) rnode = this.next;
        while(rnode.next != null){
          if(rnode.key == d.key){
            if(rnode.address == d.address && rnode.size == d.size){
              rnode.next.prev = rnode.prev;
              rnode.prev.next = rnode.next;
              rnode.prev = null;
              rnode.next = null;
              return true;
            }
          }
          rnode = rnode.next;
        }

        if(this.next == null) lnode = this.prev;
        while(lnode.prev!=null){
          if(lnode.key == d.key){
            if(lnode.address == d.address && lnode.size == d.size){
              lnode.next.prev = lnode.prev;
              lnode.prev.next = lnode.next;
              lnode.prev = null;
              lnode.next = null;
              return true;
            }
          }
          lnode = lnode.prev;
        }
        return false;
    }

    public A1List Find(int k, boolean exact)
    {
      A1List rnode = this.getFirst(), lnode = this, min_node = null;
      if(rnode == null) return null;
      if(exact == true)
      {
        //if(this.prev==null) rnode = this.next;
        while(rnode.next!=null){
          if(rnode.key == k) return rnode;
          rnode = rnode.next;
        }

        /*if(this.next==null) lnode = this.prev;
        while(lnode.prev!=null){
          if(lnode.key == k) return lnode;
          lnode = lnode.prev;
        }*/
      }

      else
      {
        //if(this.prev==null) rnode = this.next;
        while(rnode.next!=null){
          if(rnode.key >= k) return rnode;
          rnode = rnode.next;
        }
      }
      //return min_val;
      return null;
    }
    public A1List getFirst()
    {
        A1List first = this;
        if(this.prev == null)
        {
          if(this.next.next==null) return null;
          else return this.next;
        }
        else if(this.prev.prev==null && this.next == null) return null;

        while(first.prev.prev!=null) first = first.prev;

        return first;
    }

    public A1List getNext()
    {
        if(this.next!=null){
          if(this.next.next==null) return null;
        }
        return this.next;
    }

    public boolean sanity()
    {
        //checking for at least 2 nodes (sentinel nodes)
        if(this == null || (this.prev == null && this.next == null)) return false;

        //checking loop condition
        A1List head,tail,temp,temp2;
        A1List fast = this, slow = this;
        while(fast!=null && fast.next!=null && slow!=null){
          fast = fast.next.next;
          slow = slow.next;
          if(fast==slow && fast!=null) return false;
        }
        fast = slow = this;
        while(fast!=null && fast.prev!=null && slow!=null){
          fast = fast.prev.prev;
          slow = slow.prev;
          if(fast==slow && fast!=null) return false;
        }
        head = tail = this;
        while(head.prev!=null) head = head.prev;
        while(tail.next!=null) tail = tail.next;

        fast = slow = head;
        while(fast!=null && fast.next!=null && slow!=null){
          fast = fast.next.next;
          slow = slow.next;
          if(fast==slow && fast!=null) return false;
        }

        fast = slow = tail;
        while(fast!=null && fast.prev!=null && slow!=null){
          fast = fast.prev.prev;
          slow = slow.prev;
          if(fast==slow && fast!=null) return false;
        }



        //checking if head or tail of list is a sentinel node i.e.
        //has (address,size,key) = (-1,-1,-1)
        temp2 = temp = this;
        while(temp!=null) {temp2 = temp;temp = temp.prev;}
        if(temp2.key!=-1||temp2.address!=-1||temp2.size!=-1) return false;
        temp2 = temp = this;
        while(temp!=null) {temp2 = temp;temp = temp.next;}
        if(temp2.key!=-1||temp2.address!=-1||temp2.size!=-1) return false;

        //checking if for all elements if it exists,
        //node.prev.next = node and node.next.prev = node

        temp = temp2 = this;
        while(temp.prev!=null) {if(temp.prev.next!=temp) return false; temp = temp.prev;}
        while(temp2.next!=null) {if(temp2.next.prev!=temp2) return false; temp2 = temp2.next;}
        temp = head;
        while(temp!=tail){
          if(temp.next.prev!=temp) return false;
          temp = temp.next;
        }
        temp2 = tail;
        while(temp2!=head){
          if(temp2.prev.next!=temp2) return false;
          temp2 = temp2.prev;
        }


        return true;
    }

    /*public static void main(String[] args){
      A1List mylist = new A1List(),a1=null,a2=null,a3=null,a4=null,a5=null,a6=null,a7=null,a8=null,a9=null,a10=null;
      A1List mylist1 = new A1List(2,2,2);
      //mylist.next = mylist1;
      mylist.Insert(1,1,1);mylist = mylist.next;a1 = mylist;
      mylist.Insert(2,2,2);mylist = mylist.next;a2 = mylist;
      mylist.Insert(3,3,3);mylist = mylist.next;a3 = mylist;
      mylist.Insert(4,4,4);mylist = mylist.next;a4 = mylist;
      mylist.Insert(5,5,5);mylist = mylist.next;a5 = mylist;
      mylist.Insert(6,6,6);mylist = mylist.next;a6 = mylist;
      mylist.Insert(7,7,7);mylist = mylist.next;a7 = mylist;
      mylist.Insert(8,8,8);mylist = mylist.next;a8 = mylist;
      mylist.Insert(9,9,9);mylist = mylist.next;a9 = mylist;
      mylist.Insert(10,10,10);mylist = mylist.next;a10 = mylist;
      a2.next = a4;
      a4.prev = a2;
        System.out.print(a3.sanity());
    }*/


}
