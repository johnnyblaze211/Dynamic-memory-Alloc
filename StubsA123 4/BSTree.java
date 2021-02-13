// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.

    public BSTree(){
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }

    public BSTree(int address, int size, int key){
        super(address, size, key);
    }

    public BSTree Insert(int address, int size, int key)
    {
        BSTree temp = new BSTree(address, size, key);
        BSTree fake = this;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.right == null) {fake.right = temp; temp.parent = fake;}
        else{
          fake = fake.right;
          while(true){
            if(key<fake.key){
              if(fake.left==null) {fake.left = temp; temp.parent = fake;break;}
              else fake = fake.left;
            }
            else if(key>fake.key){
              if(fake.right == null) {fake.right = temp; temp.parent = fake;break;}
              else fake = fake.right;
            }
            else if(key==fake.key){
              if(address>fake.address){
                if(fake.right == null) {fake.right = temp; temp.parent = fake;break;}
                else fake = fake.right;
              }
              else if(address<fake.address){
                if(fake.left==null) {fake.left = temp; temp.parent = fake;break;}
                else fake = fake.left;
              }

              else {System.out.print("error0");return null;}

            }
          }
        }
        /*while(fake.parent!=null) fake = fake.parent;
        if(fake.right!=null) fake = fake.right;
        if(fake.parent!=null) if(fake.left!=null) System.out.print("oxox");
        */
        return temp;
    }

    public boolean Delete(Dictionary e)
    {
        BSTree fake = this;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.right==null) return false;
        else{
          fake = fake.right;
          while(true){
            if(e.key > fake.key){
              //System.out.println("yo1" + "fake.address : " + fake.address + ", e.key : " + e.key);
              if(fake.right==null) return false;
              else {fake = fake.right; continue;}
            }

            else if(e.key<fake.key){
              //System.out.println("yo2" + "fake.address : " + fake.address + ", e.key : " + e.key);
              if(fake.left==null) return false;
              else {fake = fake.left; continue;}
            }

            else if(e.key == fake.key){
              //System.out.println("yo3" + "fake.address : " + fake.address + ", e.key : " + e.key);

              if(e.address > fake.address){
                if(fake.right == null) return false;
                else fake = fake.right;
              }
              else if(e.address < fake.address){
                if(fake.left == null) return false;
                else fake = fake.left;
              }

              else{
                if(fake.left == null && fake.right == null){
                  if(fake.parent == null) return false;
                  if(fake.parent.right == fake) fake.parent.right=null;
                  else if(fake.parent.left == fake) fake.parent.left = null;
                }

                else if(fake.left == null){
                  fake.right.parent = fake.parent;
                  if(fake.parent.right == fake) fake.parent.right = fake.right;
                  else if(fake.parent.left == fake) fake.parent.left = fake.right;
                }

                else if(fake.right == null){
                  fake.left.parent = fake.parent;
                  if(fake.parent.right == fake) fake.parent.right = fake.left;
                  else if(fake.parent.left == fake) fake.parent.left = fake.left;
                }

                else{
                  BSTree fake2 = fake.right;
                  while(fake2.left!=null) fake2 = fake2.left;
                  fake.key = fake2.key;fake.address = fake2.address;fake.size = fake2.size;

                  if(fake2.right==null){
                    if(fake2.parent.right == fake2) fake2.parent.right = null;
                    else if(fake2.parent.left == fake2) fake2.parent.left = null;
                    fake2 = null;
                  }
                  else{
                    fake2.right.parent = fake2.parent;
                    if(fake2.parent.right==fake2) {fake2.parent.right = fake2.right;}
                    else if(fake2.parent.left==fake2) {fake2.parent.left = fake2.right;}
                  }

                }


                return true;
              }
            }
          }
        }
    }

    public BSTree Find(int key, boolean exact)
    {
        BSTree fake = this;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.right==null) return null;
        else fake = fake.right;
        if(exact){
          BSTree min_node = null;
          while(true){
            if(key>fake.key){
              if(fake.right == null) break;
              else fake = fake.right;
            }
            else if(key<fake.key){
              if(fake.left==null) break;
              else fake = fake.left;
            }
            else if(key==fake.key){
              min_node = fake;
              if(fake.left==null) break;
              else{
                fake = fake.left;
                continue;
              }
            }
          }
          return min_node;
        }
        else if(!(exact)){
          BSTree min_node = null;
          int min_key = 99999999;
          while(true){
            if(key>fake.key){
              if(fake.right==null) break;
              else fake = fake.right;
            }
            else if(key<=fake.key){
              min_node = fake;
              if(fake.left==null) break;
              else {fake = fake.left; continue;}
            }
          }

          return min_node;
        }
        return null;
    }



    public BSTree getFirst()
    {
        BSTree fake = this;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.right == null) return null;
        fake = fake.right;
        while(fake.left!=null) fake = fake.left;
        return fake;
    }

    public BSTree getNext()
    {
        BSTree fake = this;
        if(this.parent == null) return null;
        if(this.right!=null){
          fake = fake.right;
          while(fake.left!=null) fake = fake.left;
          return fake;
        }
        else{
          while(true){
            if(fake.parent == null) return null;
            else if(fake.parent.left==fake) return fake.parent;
            else if(fake.parent.right==fake) fake = fake.parent;
            else {System.out.print("Hello"); return null;}
          }
        }

    }

    private BSTree duplicateStr(BSTree treenode, BSTree newtree){
      if(treenode.left!=null){
        BSTree left = new BSTree(treenode.left.address, treenode.left.size, treenode.left.key);
        newtree.left = left;
        left.parent = newtree;
        this.duplicateStr(treenode.left, newtree.left);
      }

      if(treenode.right!=null){
        BSTree right = new BSTree(treenode.right.address, treenode.right.size, treenode.right.key);
        newtree.right = right;
        right.parent = newtree;
        this.duplicateStr(treenode.right, newtree.right);
      }

      return newtree;

    }

    private boolean cycleCheck(BSTree newtree){
      if(newtree.key == -99999999) return false;
      else{
        newtree.key = -99999999;
        if(newtree.left==null && newtree.right==null) return true;
        else if(newtree.left == null) return this.cycleCheck(newtree.right);
        else if(newtree.right == null) return this.cycleCheck(newtree.left);
        else return this.cycleCheck(newtree.left) && this.cycleCheck(newtree.right);
      }
    }

    private boolean isValidNode(BSTree treenode){
      if(treenode.right == null && treenode.left == null) return true;
      else if(treenode.left == null){
        if(treenode.right.parent != treenode) return false;
        else return this.isValidNode(treenode.right);
      }
      else if(treenode.right == null){
        if(treenode.left.parent!=treenode) return false;
        else return this.isValidNode(treenode.left);
      }
      else{
        if(treenode.left.parent!=treenode) return false;
        else if(treenode.right.parent!=treenode) return false;
        else return this.isValidNode(treenode.left) && this.isValidNode(treenode.right);
      }
    }

    private boolean BScheck(BSTree treenode){
      if(treenode.left == null && treenode.right == null) return true;
      else if(treenode.left == null){
        if((treenode.right.key <  treenode.key) || ((treenode.right.key==treenode.key) && (treenode.right.address < treenode.address))) return false;
        else return this.BScheck(treenode.right);
      }
      else if(treenode.right == null){
        if((treenode.left.key > treenode.key) || ((treenode.left.key == treenode.key) && (treenode.left.address > treenode.address))) return false;
        else return this.BScheck(treenode.left);
      }
      else{
        if((treenode.right.key < treenode.key) || ((treenode.right.key == treenode.key) && (treenode.right.address < treenode.address))) return false;
        else if((treenode.left.key > treenode.key) || ((treenode.left.key == treenode.key) && (treenode.left.address > treenode.address))) return false;
        else return this.BScheck(treenode.left) && this.BScheck(treenode.right);
      }
    }




    public boolean sanity()
    {
        BSTree fake = this;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.key!=-1 || fake.address!=-1 || fake.size!=-1) return false;
        if(fake.left!=null) return false;
        if(fake.right.parent!=fake) return false;
        if(fake.right == null) return true;

        else{
          fake = fake.right;

          //checking if there are any cycles in the tree
          BSTree newfake = new BSTree(fake.address, fake.size, fake.key);
          BSTree cycleChk = fake.duplicateStr(fake, newfake);
          if(!(this.cycleCheck(cycleChk))) return false;

          //checking for node.right.parent == node.left.parent == node
          if(!(this.isValidNode(fake))) return false;

          //checking if binary search property holds
          if(!(this.BScheck(fake))) return false;


        }
        return true;
    }

    private BSTree Insert2(){
        return null;
    }

    private void Insert2(int k){
      this.Insert(k,22,22);
    }
    private void Insert3(int k){
      this.Insert(k,24,24);
    }

    private Boolean Delete2(int k){
      return this.Delete(new BSTree(k,k,k));
    }

    private void Preorder(BSTree t){
      if(t==null) return;
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");
      this.Preorder(t.left);
      this.Preorder(t.right);

    }

    private void Inorder(BSTree t){
      if(t==null) return;
      this.Inorder(t.left);
      System.out.print(t.key + " ");
      this.Inorder(t.right);

    }

    private void Inorder2(BSTree t){
      if(t==null) return;
      this.Inorder2(t.left);
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");
      this.Inorder2(t.right);

    }

    private void Postorder(BSTree t){
      if(t==null) return;
      this.Postorder(t.left);
      this.Postorder(t.right);
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");

    }

    public static void main(String[] args){
      BSTree t = new BSTree(), torig = t;

      t.Insert2(8);
      t.Insert2(3);
      t.Insert3(10);
      t.Insert3(1);
      t.Insert(6,6,6);
      t.Insert(14,24,24);
      t.Insert3(4);
      t.Insert2(7);
      t.Insert2(13);
      t = t.right.right;
      Dictionary s = new BSTree(14,24,24);
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");
      System.out.println(t.Delete(s));

      torig.Preorder(torig);
      System.out.print("\n");

      torig.Inorder2(torig);
      System.out.print("\n");

      //torig.Postorder(torig);
      System.out.print("\n");
      System.out.print("\n\n\n");
      //System.out.print(t.Find(3, false).key);
      System.out.print(t.Find(22,false).address);
    }



}
