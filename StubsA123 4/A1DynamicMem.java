// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {

    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if(blockSize<=0) return -1;
        Dictionary freeList = this.freeBlk;
        Dictionary foundNode = freeList.Find(blockSize, false);

        if(foundNode!=null){
          int returnaddr = foundNode.address;
          if(foundNode.size==blockSize){
            this.allocBlk.Insert(foundNode.address, foundNode.size, foundNode.address);
            foundNode.Delete(foundNode);

          }

          else if(foundNode.size > blockSize){
            this.allocBlk.Insert(foundNode.address, blockSize, foundNode.address);
            this.freeBlk.Insert(foundNode.address + blockSize, foundNode.size - blockSize, foundNode.size - blockSize);
            foundNode.Delete(foundNode);
          }

          return returnaddr;
        }

        return -1;
    }

    public int Free(int startAddr) {
        Dictionary allocList = this.allocBlk;
        Dictionary foundNode = allocList.Find(startAddr, true);

        if(foundNode!=null){
          Dictionary freeList = this.freeBlk;
          freeList.Insert(foundNode.address, foundNode.size, foundNode.size);

          foundNode.Delete(foundNode);


          return 0;
        }
        return -1;
    }

}
