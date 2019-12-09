package andbas.Ch11TabHost2;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter
{
	private Action Action;
	private LayoutInflater myInflater;
	private ArrayList<HashMap<String, String>> list = null;
	private ViewTag viewTag;
	 
	public MyAdapter(Action context, ArrayList<HashMap<String, String>> list) 
	{
	    this.Action = context;
	    this.myInflater = LayoutInflater.from(context);
	    this.list = list;
	}
	 
	public int getCount() {
	    return list.size();
	}
	 
	public Object getItem(int position) {
	    return list.get(position);
	}
	 
	public long getItemId(int position) {
	    return Long.valueOf(list.get(position).get("id"));
	}
	 
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	    if (convertView == null) {
	        // ���olistItem�e�� view
	        convertView = myInflater.inflate(R.layout.mylistview, null);
	 
	        // �غclistItem���eview
	        viewTag = new ViewTag(
	                (TextView) convertView.findViewById(R.id.textView1),
	                (Button) convertView.findViewById(R.id.button1));
	 
	        // �]�m�e�����e
	        convertView.setTag(viewTag);
	 
	    } else {
	        viewTag = (ViewTag) convertView.getTag();
	    }
	     
	    viewTag.text1.setText(list.get(position).get("name"));
	    //�]�w���s��ť�ƥ�ζǤJ Action ����
	    viewTag.btn1.setOnClickListener(new ItemButton_Click(this.Action, position));
	     
	    return convertView;
	}
	 
	public class ViewTag {
	    TextView text1;
	    TextView btn1;
	     
	    public ViewTag(TextView textview1, Button button1) {
	        this.text1 = textview1;
	        this.btn1 = button1;
	    }
	}
	 
	//�ۭq���s��ť�ƥ�
	class ItemButton_Click implements OnClickListener {
	    private int position;
	    private Action Action;
	     
	    ItemButton_Click(Action context, int pos) {
	        this.Action = context;
	        position = pos;
	    }
	 
	    public void onClick(View v) 
	    {
	    	Action.DeleteData(list.get(position).get("id"));
	    }
	}
}