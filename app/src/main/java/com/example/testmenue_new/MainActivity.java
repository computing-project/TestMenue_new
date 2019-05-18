package com.example.testmenue_new;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnSuper;
    ArrayList<Criteria> criteriaList;

    /** Called when the activity is first created. */
    ExpandableListView expandableList;
    TreeViewAdapter adapter;
    SuperTreeViewAdapter superAdapter;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSuper=(Button)this.findViewById(R.id.btnSuper);
        adapter=new TreeViewAdapter(this,TreeViewAdapter.PaddingLeft>>1);
    }


    public void showCriteria(View v) {

        criteriaList = DefaultCriteriaList.getDefaultCriteriaList();

        LayoutInflater layoutInflater = LayoutInflater.from(this);//获得layoutInflater对象
        final View view2 = layoutInflater.from(this).inflate(R.layout.dialog_show_criteria, null);//获得view对象


        adapter=new TreeViewAdapter(this,TreeViewAdapter.PaddingLeft>>1);
        superAdapter=new SuperTreeViewAdapter(this,stvClickEvent);
        expandableList=(ExpandableListView) view2.findViewById(R.id.ExpandableListView01);

        adapter.RemoveAll();
        adapter.notifyDataSetChanged();
        superAdapter.RemoveAll();
        superAdapter.notifyDataSetChanged();

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

        Dialog dialog = new android.app.AlertDialog.Builder(this).setView(view2).create();

        dialog.show();
    }


    ExpandableListView.OnChildClickListener stvClickEvent=new ExpandableListView.OnChildClickListener(){

        @Override
        public boolean onChildClick(ExpandableListView parent,
                                    View v, int groupPosition, int childPosition,
                                    long id) {
            String str="parent id:"+String.valueOf(groupPosition)+",children id:"+String.valueOf(childPosition);
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();

            return false;
        }

    };



}
