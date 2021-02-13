// Class: Height balanced AVL Tree
// Binary Search Tree
import java.lang.Math;
import java.util.Random;
public class AVLTree extends BSTree {

    private AVLTree left, right;     // Children.
    private AVLTree parent;          // Parent pointer.
    private int height;  // The height of the subtree

    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.

    }

    public AVLTree(int address, int size, int key) {
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions.
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    /*private AVLTree[] sortNodes(AVLTree fake1, AVLTree fake2, AVLTree fake3){
      AVLTree[] arr = new AVLTree[3];
      AVLTree t;
      arr[0] = fake1; arr[1] = fake2; arr[2] = fake3;
      if(arr[0].key>arr[1].key || (arr[0].key == arr[1].key && arr[0].address > arr[1].address)){
        t = arr[0];
        arr[0] = arr[1];
        arr[1] = t;
      }
      if(arr[1].key>arr[2].key || (arr[1].key == arr[2].key && arr[1].address > arr[2].address)){
        t = arr[1];
        arr[1] = arr[2];
        arr[2] = t;
      }
      if(arr[0].key>arr[1].key || (arr[0].key == arr[1].key && arr[0].address > arr[1].address)){
        t = arr[0];
        arr[0] = arr[1];
        arr[1] = t;

      }

      return arr;

    }

    private AVLTree[] sortSubs(AVLTree sub1, AVLTree sub2, AVLTree sub3, AVLTree sub4){
      int countnull = 0;
      if(sub1 == null) countnull++;
      if(sub2 == null) countnull++;
      if(sub3 == null) countnull++;
      if(sub4 == null) countnull++;
      if(countnull == 4) return null;
      AVLTree[] arr = new AVLTree[4 - countnull];
      for(int i = 4 - countnull;i > 0;i--){
        for(int j = 0; j < i;j++){

        }
      }
    }*/

    private AVLTree leftleftR(AVLTree fake, AVLTree prev1, AVLTree prev2){
      boolean rightchild = false;
      if(fake.parent.right == fake) rightchild = true;

      fake.left = prev1.right;
      if(prev1.right!=null)prev1.right.parent = fake;
      prev1.right = fake;
      prev1.parent = fake.parent;
      fake.parent = prev1;
      fake.height-=1;
      if(rightchild) prev1.parent.right = prev1;
      else prev1.parent.left = prev1;

      return prev1;
    }

    private AVLTree rightrightR(AVLTree fake, AVLTree prev1, AVLTree prev2){
      boolean rightchild = false;
      if(fake.parent.right == fake) rightchild = true;

      fake.right = prev1.left;
      if(prev1.left!=null)prev1.left.parent = fake;
      prev1.left = fake;
      prev1.parent = fake.parent;
      fake.parent = prev1;
      fake.height-=1;
      if(rightchild) prev1.parent.right = prev1;
      else prev1.parent.left = prev1;

      return prev1;


    }

    private AVLTree leftrightR(AVLTree fake, AVLTree prev1,  AVLTree prev2){
      prev1.right = prev2.left;
      if(prev2.left!=null) prev2.left.parent = prev1;
      prev2.left = prev1;
      prev2.parent = fake;
      prev1.parent = prev2;
      fake.left = prev2;
      prev2.height++;
      prev1.height--;

      return this.leftleftR(fake, prev2, prev1);
    }

    private AVLTree rightleftR(AVLTree fake, AVLTree prev1, AVLTree prev2){
      prev1.left = prev2.right;
      if(prev2.right!=null) prev2.right.parent = prev1;
      prev2.right = prev1;
      prev2.parent = fake;
      prev1.parent = prev2;
      fake.right = prev2;
      prev2.height++;
      prev1.height--;

      return this.rightrightR(fake, prev2, prev1);
    }

    public AVLTree Insert(int address, int size, int key)
    {
        AVLTree temp = new AVLTree(address, size, key), prev1=null, prev2=null;
        AVLTree fake = this;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.right == null) {fake.right = temp; temp.parent = fake; return temp;}
        fake = fake.right;
        while(true){
          if(key<fake.key || (key==fake.key && address < fake.address)){
            if(fake.left==null) {fake.left = temp; temp.parent = fake;break;}
            else fake = fake.left;
          }
          else if(key>fake.key || (key == fake.key && address>fake.address)){
            if(fake.right==null){fake.right = temp;temp.parent = fake; break;}
            else fake = fake.right;
          }
        }


        if(fake.height == 0) {
          fake.height = 1;
          fake = fake.parent;
        }
        else return temp;

        //boolean balanced = true;
        int lheight,rheight,temp_h1,temp_h2;
        boolean ll, rr;

        while(fake.parent!=null){
          lheight = -1; rheight = -1;
          if(fake.left!=null) lheight = fake.left.height;
          if(fake.right!=null) rheight = fake.right.height;
          if(lheight - rheight > 1){
            prev1 = fake.left;
            if(prev1.left==null) {prev2 = prev1.right; ll = false;}
            else if(prev1.left.height == prev1.height - 1) {prev2 = prev1.left; ll = true;}
            else {prev2 = prev1.right; ll = false;}
            if(prev2 == null){fake = fake.parent;continue;}

            if(ll){
              temp_h1 = -1; temp_h2 = -1;
              this.leftleftR(fake, prev1, prev2);
              if(prev2.left!=null) temp_h1 = prev2.left.height;
              if(prev2.right!=null) temp_h2 = prev2.right.height;
              prev2.height = 1 + Math.max(temp_h1, temp_h2);

              temp_h1 = -1; temp_h2 = -1;
              if(fake.left!=null) temp_h1 = fake.left.height;
              if(fake.right!=null) temp_h2 = fake.right.height;
              fake.height = 1 + Math.max(temp_h1, temp_h2);

              prev1.height = 1 + Math.max(prev2.height, fake.height);

              fake = prev1.parent;
            }

            else{
              temp_h1 = -1; temp_h2 = -1;
              this.leftrightR(fake, prev1, prev2);
              if(prev1.left!=null) temp_h1 = prev1.left.height;
              if(prev1.right!=null) temp_h2 = prev1.right.height;
              prev1.height = 1 + Math.max(temp_h1, temp_h2);

              temp_h1 = -1; temp_h2 = -1;
              if(fake.left!=null) temp_h1 = fake.left.height;
              if(fake.right!=null) temp_h2 = fake.right.height;
              fake.height = 1 + Math.max(temp_h1, temp_h2);

              prev2.height = 1 + Math.max(prev1.height, fake.height);

              fake = prev2.parent;
            }


          }

          else if(rheight - lheight > 1){
            prev1 = fake.right;
            if(prev1.right==null){prev2 = prev1.left; rr = false;}
            else if(prev1.right.height == prev1.height - 1){prev2 = prev1.right; rr = true;}
            else {prev2 = prev1.left; rr = false;}
            if(prev2 == null){fake = fake.parent;continue;}

            if(rr){
              temp_h1 = -1; temp_h2 = -1;
              this.rightrightR(fake,prev1,prev2);
              if(prev2.left!=null) temp_h1 = prev2.left.height;
              if(prev2.right!=null) temp_h2 = prev2.right.height;
              prev2.height = 1 + Math.max(temp_h1, temp_h2);

              temp_h1 = -1; temp_h2 = -1;
              if(fake.left!=null) temp_h1 = fake.left.height;
              if(fake.right!=null) temp_h2 = fake.right.height;
              fake.height = 1 + Math.max(temp_h1, temp_h2);

              prev1.height = 1 + Math.max(prev2.height, fake.height);

              fake = prev1.parent;

            }

            else{
              temp_h1 = -1; temp_h2 = -1;
              this.rightleftR(fake, prev1, prev2);

                if(prev1.left!=null) temp_h1 = prev1.left.height;
                if(prev1.right!=null) temp_h2 = prev1.right.height;
                prev1.height = 1 + Math.max(temp_h1, temp_h2);

                temp_h1 = -1; temp_h2 = -1;
                if(fake.left!=null) temp_h1 = fake.left.height;
                if(fake.right!=null) temp_h2 = fake.right.height;
                fake.height = 1 + Math.max(temp_h1, temp_h2);

                prev2.height = 1 + Math.max(prev1.height, fake.height);

                fake = prev2.parent;
            }
          }

          else{
            if(Math.max(lheight, rheight) + 1 == fake.height) break;
            else {fake.height+=1;
            fake = fake.parent;}
          }

        }

        return temp;

    }

    public boolean Delete(Dictionary e)
    {
        AVLTree fake = this, prev1 = null, prev2 = null;
        while(fake.parent!=null) fake = fake.parent;
        if(fake.right==null) return false;
        fake = fake.right;
        while(true){
          if(e.key>fake.key || (e.key==fake.key && e.address>fake.address)){
            if(fake.right == null) return false;
            else fake = fake.right;
          }

          else if(e.key<fake.key || (e.key==fake.key && e.address<fake.address)){
            if(fake.left == null) return false;
            else fake = fake.left;
          }

          else{
            if(fake.right == null){
              if(fake.parent.right==fake) fake.parent.right = fake.left;
              else fake.parent.left = fake.left;
              if(fake.left!=null) fake.left.parent = fake.parent;
              if(fake.left==null) fake = fake.parent;
              else fake = fake.left;
            }

            else{
              AVLTree fake2 = fake.right;
              while(fake2.left != null) fake2 = fake2.left;
              fake.key = fake2.key;fake.size = fake2.size;fake.address = fake2.address;
              if(fake2.parent.right == fake2) fake2.parent.right = fake2.right;
              else fake2.parent.left = fake2.right;
              if(fake2.right !=null) fake2.right.parent = fake2.parent;
              if(fake2.right!=null) fake2 = fake2.right;
              else fake2 = fake2.parent;
              fake = fake2;
            }

            /*if(fake.parent==null) return true;
            if(fake.height == 1) fake.height = 0;
            fake = fake.parent;*/


            int lheight,rheight,temp_h1,temp_h2;
            boolean ll, rr;

            while(fake.parent!=null){
              lheight = -1; rheight = -1; temp_h1 = -1; temp_h2 = -1;
              if(fake.left!=null) lheight = fake.left.height;
              if(fake.right!=null) rheight = fake.right.height;

              if(lheight-rheight>1)
              {
                prev1 = fake.left;
                if(prev1.left==null) {prev2 = prev1.right; ll = false;}
                else if(prev1.left.height == prev1.height - 1) {prev2 = prev1.left; ll = true;}
                else {prev2 = prev1.right; ll = false;}
                if(prev2 == null){fake = fake.parent;continue;}

                if(ll){
                  temp_h1 = -1; temp_h2 = -1;
                  this.leftleftR(fake, prev1, prev2);
                  if(prev2.left!=null) temp_h1 = prev2.left.height;
                  if(prev2.right!=null) temp_h2 = prev2.right.height;
                  prev2.height = 1 + Math.max(temp_h1, temp_h2);

                  temp_h1 = -1; temp_h2 = -1;
                  if(fake.left!=null) temp_h1 = fake.left.height;
                  if(fake.right!=null) temp_h2 = fake.right.height;
                  fake.height = 1 + Math.max(temp_h1, temp_h2);

                  prev1.height = 1 + Math.max(prev2.height, fake.height);

                  fake = prev1.parent;
                }

                else{
                  temp_h1 = -1; temp_h2 = -1;
                  this.leftrightR(fake, prev1, prev2);
                  if(prev1.left!=null) temp_h1 = prev1.left.height;
                  if(prev1.right!=null) temp_h2 = prev1.right.height;
                  prev1.height = 1 + Math.max(temp_h1, temp_h2);

                  temp_h1 = -1; temp_h2 = -1;
                  if(fake.left!=null) temp_h1 = fake.left.height;
                  if(fake.right!=null) temp_h2 = fake.right.height;
                  fake.height = 1 + Math.max(temp_h1, temp_h2);

                  prev2.height = 1 + Math.max(prev1.height, fake.height);

                  fake = prev2.parent;
                }
              }

              else if(rheight - lheight>1)
              {
                prev1 = fake.right;
                if(prev1.right==null){prev2 = prev1.left; rr = false;}
                else if(prev1.right.height == prev1.height - 1){prev2 = prev1.right; rr = true;}
                else {prev2 = prev1.left; rr = false;}
                if(prev2 == null){fake = fake.parent;continue;}

                if(rr){
                  temp_h1 = -1; temp_h2 = -1;
                  this.rightrightR(fake,prev1,prev2);
                  if(prev2.left!=null) temp_h1 = prev2.left.height;
                  if(prev2.right!=null) temp_h2 = prev2.right.height;
                  prev2.height = 1 + Math.max(temp_h1, temp_h2);

                  temp_h1 = -1; temp_h2 = -1;
                  if(fake.left!=null) temp_h1 = fake.left.height;
                  if(fake.right!=null) temp_h2 = fake.right.height;
                  fake.height = 1 + Math.max(temp_h1, temp_h2);

                  prev1.height = 1 + Math.max(prev2.height, fake.height);

                  fake = prev1.parent;

                }

                else{
                  temp_h1 = -1; temp_h2 = -1;
                  this.rightleftR(fake, prev1, prev2);

                    if(prev1.left!=null) temp_h1 = prev1.left.height;
                    if(prev1.right!=null) temp_h2 = prev1.right.height;
                    prev1.height = 1 + Math.max(temp_h1, temp_h2);

                    temp_h1 = -1; temp_h2 = -1;
                    if(fake.left!=null) temp_h1 = fake.left.height;
                    if(fake.right!=null) temp_h2 = fake.right.height;
                    fake.height = 1 + Math.max(temp_h1, temp_h2);

                    prev2.height = 1 + Math.max(prev1.height, fake.height);

                    fake = prev2.parent;
                }
              }

              else
              {
                if(Math.max(lheight, rheight) + 1 == fake.height) break;
                else{
                  fake.height-=1;
                  fake = fake.parent;
                }
              }
            }



            return true;
          }
        }

    }

    public AVLTree Find(int key, boolean exact)
    {
      AVLTree fake = this;
      while(fake.parent!=null) fake = fake.parent;
      if(fake.right==null) return null;
      else fake = fake.right;
      if(exact){
        AVLTree min_node = null;
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
        AVLTree min_node = null;
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

    public AVLTree getFirst()
    {
      AVLTree fake = this;
      while(fake.parent!=null) fake = fake.parent;
      if(fake.right == null) return null;
      fake = fake.right;
      while(fake.left!=null) fake = fake.left;
      return fake;
    }

    public AVLTree getNext()
    {
      AVLTree fake = this;
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

    private AVLTree duplicateStr(AVLTree treenode, AVLTree newtree){
      if(treenode.left!=null){
        AVLTree left = new AVLTree(treenode.left.address, treenode.left.size, treenode.left.key);
        newtree.left = left;
        left.parent = newtree;
        this.duplicateStr(treenode.left, newtree.left);
      }

      if(treenode.right!=null){
        AVLTree right = new AVLTree(treenode.right.address, treenode.right.size, treenode.right.key);
        newtree.right = right;
        right.parent = newtree;
        this.duplicateStr(treenode.right, newtree.right);
      }

      return newtree;

    }

    private boolean cycleCheck(AVLTree newtree){
      if(newtree.key == -99999999) return false;
      else{
        newtree.key = -99999999;
        if(newtree.left==null && newtree.right==null) return true;
        else if(newtree.left == null) return this.cycleCheck(newtree.right);
        else if(newtree.right == null) return this.cycleCheck(newtree.left);
        else return this.cycleCheck(newtree.left) && this.cycleCheck(newtree.right);
      }
    }

    private boolean isValidNode(AVLTree treenode){
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

    private boolean BScheck(AVLTree treenode){
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

    private boolean AVLcheck(AVLTree treenode){
      int lheight = -1, rheight = -1;
      boolean lnull = true, rnull = true;
      if(treenode.left != null) {lheight = treenode.left.height; lnull = false;}
      if(treenode.right != null) {rheight = treenode.right.height; rnull = false;}
      if(lheight - rheight > 1|| rheight - lheight > 1) return false;
      else{
        if(lnull && rnull) return true;
        else if(lnull) return this.AVLcheck(treenode.right);
        else if(rnull) return this.AVLcheck(treenode.left);
        else return this.AVLcheck(treenode.left) && this.AVLcheck(treenode.right);
      }
    }

    private boolean chkbalance(AVLTree treenode){
      if(treenode == null) return true;
      else{
        if(treenode.left == null && treenode.right == null) {if(treenode.height == 0) return true; else return false;}

        else if(treenode.left == null) {
          if(treenode.right.height > 1) return false;
          else if(treenode.height!=treenode.right.height+1) return false;
          else return this.chkbalance(treenode.right);}

        else if(treenode.right == null) {
          if(treenode.left.height > 1) return false;
          else if(treenode.height != treenode.left.height+1) return false;
          else return this.chkbalance(treenode.left);}

        else{
          if((treenode.left.height - treenode.right.height) > 1 || (treenode.left.height - treenode.right.height) < -1) return false;
          else if(treenode.height!=1 + Math.max(treenode.right.height, treenode.left.height)) return false;
          else {return (this.chkbalance(treenode.left) && this.chkbalance(treenode.right));}
        }
      }
    }




    public boolean sanity()
    {
        if(this == null) return false;
        AVLTree fake2 = this, fake = this;
        boolean skip = false;
        if(fake2.parent == null) skip = true;
        else {fake2 = fake2.parent.parent; fake = fake.parent;}
        while(!skip){
          if(fake2 == null) break;
          else if(fake2.parent == null) break;
          else if(fake == fake2) return false;
          else{
            fake2 = fake2.parent.parent;
            fake = fake.parent;
          }
        }

        while(fake.parent!=null) fake = fake.parent;
        if(fake.key!=-1 || fake.address!=-1 || fake.size!=-1) return false;
        if(fake.right == null) return true;
        if(fake.left!=null) return false;
        if(fake.right.parent!=fake) return false;

          fake = fake.right;

          //checking if there are any cycles in the tree
          AVLTree newfake = new AVLTree(fake.address, fake.size, fake.key);
          AVLTree cycleChk = fake.duplicateStr(fake, newfake);
          if(!(this.cycleCheck(cycleChk))) return false;

          //checking for node.right.parent == node.left.parent == node
          if(!(this.isValidNode(fake))) return false;

          //checking if binary search property holds
          if(!(this.BScheck(fake))) return false;

          //checking if AVL property holds
          //if(!(this.chkbalance(fake))) return false;


        return true;
    }



    private void Insert2(int k){
      this.Insert(k, k, k);
    }

    private void Delete2(int k){
      Dictionary e = new AVLTree(k,k,k);
      this.Delete(e);
    }

    private void Preorder(AVLTree t){
      if(t==null) return;
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");
      this.Preorder(t.left);
      this.Preorder(t.right);

    }

    private void Inorder(AVLTree t){
      if(t==null) return;
      this.Inorder(t.left);
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");
      this.Inorder(t.right);

    }

    private void Postorder(AVLTree t){
      if(t==null) return;
      this.Postorder(t.left);
      this.Postorder(t.right);
      System.out.print("(" + t.address + ", " + t.size + ", " + t.key + ") ");

    }

    public static void main(String[] args){
      AVLTree t = new AVLTree();
      Random rand = new Random();
      Random rand2 = new Random();
      Random rand3 = new Random();
      rand.setSeed(5);
      rand2.setSeed(5);
      rand3.setSeed(5);

      for(int i = 1;i < 20; i++){System.out.print(rand3.nextInt(400) + " ");}
      System.out.println();
      //for(int i = 1; i < 20; i++) {int j = rand.nextInt(400);t.Insert2(j);}
      t.Insert2(287);
      t.Insert2(92);
      t.Insert2(274);
      t.Insert2(224);
      t.Insert2(106);
      t.Insert2(5);
      t.Insert2(254);
      t.Insert2(291);
      t.Insert2(122);
      t.Insert2(121);
      t.Insert2(231);
      //for(int i = 1; i < 20;i++) {int j = rand2.nextInt(400); if(i%2==0)t.Delete2(j);}
      //t.Delete2(92);
      //t.Delete2(224);
      //t.Delete2(5);

      //t.Delete2(5);
      t = t.right;
      t.Preorder(t);
      System.out.print("\n");
      t.Inorder(t);
      System.out.print("\n");
      System.out.println(t.height);
      System.out.print(t.sanity());



    }
}
