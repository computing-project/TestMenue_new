package com.example.testmenue_new;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class testExpandableList extends Activity {

    ArrayList<Criteria> criteriaList;

    /** Called when the activity is first created. */
    ExpandableListView expandableList;
    TreeViewAdapter adapter;
    SuperTreeViewAdapter superAdapter;
    Button btnSuper;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        criteriaList = DefaultCriteriaList.getDefaultCriteriaList();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_show_criteria);
        this.setTitle("ExpandableListView练习----hellogv");
//        btnSuper=(Button)this.findViewById(R.id.btnSuper);
//        btnSuper.setOnClickListener(new ClickEvent());
        adapter=new TreeViewAdapter(this,TreeViewAdapter.PaddingLeft>>1);
        superAdapter=new SuperTreeViewAdapter(this,stvClickEvent);
        expandableList=(ExpandableListView) testExpandableList.this.findViewById(R.id.ExpandableListView01);
//    }
//
//    class ClickEvent implements View.OnClickListener{
//
//        @Override
//        public void onClick(View v) {
            adapter.RemoveAll();
            adapter.notifyDataSetChanged();
            superAdapter.RemoveAll();
            superAdapter.notifyDataSetChanged();

//            if(v==btnSuper){
                List<SuperTreeViewAdapter.SuperTreeNode> superTreeNode = superAdapter.GetTreeNode();
                for(int i=0;i<criteriaList.get(0).getSubsectionList().size();i++)//第一层
                {
                    SuperTreeViewAdapter.SuperTreeNode superNode=new SuperTreeViewAdapter.SuperTreeNode();
                    superNode.parent=criteriaList.get(0).getSubsectionList().get(i).getName();

//第二层
                    for(int ii=0;ii<criteriaList.get(0).getSubsectionList().get(i).getShortTextList().size();ii++)
                    {
                        TreeViewAdapter.TreeNode node=new TreeViewAdapter.TreeNode();
                        node.parent=criteriaList.get(0).getSubsectionList().get(i).getShortTextList().get(ii).getName();//第二级菜单的标题

                        for(int iii=0;iii<criteriaList.get(0).getSubsectionList().get(i).getShortTextList().get(ii).getLongtext().size();iii++)//第三级菜单
                        {
                            node.childs.add(criteriaList.get(0).getSubsectionList().get(i).getShortTextList().get(ii).getLongtext().get(iii));
                        }
                        superNode.childs.add(node);
                    }
                    superTreeNode.add(superNode);

                }
                superAdapter.UpdateTreeNode(superTreeNode);
                expandableList.setAdapter(superAdapter);
//            }
        }
//    }

    /** 三级树形菜单的事件不再可用，本函数由三级树形菜单的子项（二级菜单）进行回调 */
    OnChildClickListener stvClickEvent=new OnChildClickListener(){

        @Override
        public boolean onChildClick(ExpandableListView parent,
                                    View v, int groupPosition, int childPosition,
                                    long id) {
            String str="parent id:"+String.valueOf(groupPosition)+",children id:"+String.valueOf(childPosition);
            Toast.makeText(testExpandableList.this, str, 300).show();

            return false;
        }

    };
}