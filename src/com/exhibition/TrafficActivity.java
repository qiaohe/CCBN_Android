package com.exhibition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exhibition.interfaces.ActivityInterface;
import com.exhibition.listener.HomeClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TrafficActivity extends Activity implements ActivityInterface,
			OnItemClickListener{
	private Button btHome;
	private ListView listView;
	private TextView tvTitle;
	private String title;
	private String[] titles = { "航空","轨道交通", "铁路","公交","公路",
			"出租车", "水上交通"};
	private List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_traffic);
		
		initData();
		findView();
		addAction(); 
	}
	public void initData(){
		for(int i = 0; i < titles.length; i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("title", titles[i]);
			data.add(map);
		}
		title = getIntent().getStringExtra("title");
	}
	@Override
	public void findView() {
		btHome = (Button) this.findViewById(R.id.home_button_second);
		tvTitle = (TextView) this.findViewById(R.id.title_text_second);
		listView = (ListView) this.findViewById(R.id.activity_traffic_lv);
	}
	@Override
	public void addAction() {
		btHome.setOnClickListener(new HomeClickListener(this));
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), 
				data, R.layout.listview_item, 
				new String[]{"title"}, new int[]{R.id.item_text});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		tvTitle.setText(title);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(TrafficActivity.this,TrafficItemActivity.class);
		switch(position){
		case 0:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.airport);
			intent.putExtra("intro", "      上海是中国大陆同时拥有两个民用国际机场的城市。一座是上海浦东国际机场，一座是上海虹桥国际机场，分别位于城市的东西两侧。为了适应“一市两场”的上海空港运行新格局，1998年5月28日，经上海市人民政府批准，组建了上海机场（集团）有限公司，统一经营管理上海浦东和虹桥两大国际机场。");
			break;
		case 1:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.tubemap);
			intent.putExtra("intro", "      上海轨道交通[1]，又称上海地铁，其第一条线路于1995年4月10日正式运营，是继北京地铁、天津地铁建成通车后中国大陆投入运营的第三个城市轨道交通系统。上海轨道交通线网已开通运营12条线、291座车站，运营里程达439.1公里（273英里，不含磁浮示范线29．863公里），近期及远期规划则分别达到510公里和970公里。截止2013年1月1日，上海轨道交通通车的总长超过400公里，位居世界第三，居首尔、北京之后。");
			break;
		case 2:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.train);
			intent.putExtra("intro", "      东起沪宁、沪昆线上海站，西与南昌铁路局在沪昆线新塘边站、皖赣线倒湖站、合九线孔垄站、南与南昌铁路局在温福铁路客运专线在福鼎站分界、西 与武汉铁路局在京九线淮滨站、合武线墩义堂站，北与济南铁路局在京沪线利国站、胶新线新沂西站，与郑州铁路局在陇海线虞城县站分界；与阜阳地方铁路在漯阜线阜阳北站分界");
			break;
		case 3:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.bus);
			intent.putExtra("intro", "      上海市目前拥有数千条公共汽车和电车线路，是上海主要交通方式之一。随着上海轨道交通线路的逐渐开通，上海市交通管理局的交通发展思路演变为“轨交为主、公交为辅”。这一思路导致许多经典的线路被撤销或缩线。2010年3月，单月内撤销的公交线路数量创下了历史记录，上海公交已经辉煌不再。");
			break;
		case 4:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.road);
			intent.putExtra("intro", "      沪嘉高速主线改造工程今天凌晨起正式实施，从今天起沪嘉高速（S20-南门段）每天6：00-22：00时禁止大型号牌货运机动车辆通行。记者从路政部门获悉，昨夜今晨，工程施工单位已经开始设置施工区域的隔离设施。" + 
									"记者今天一早在现场看到，G1501上海绕城高速转沪嘉高速（市区方向）的匝道已经采用水泥隔离墩封闭了1#车道，大约600米长的距离；同时G1501沪嘉收费站至S5沪嘉马陆匝道（市区方向）的路面车道进行了重新划分。虽然沪嘉高速部分路段减少了一根车道，但是今天早高峰的车辆通行情况还是比较正常，没有出现拥堵现象。");
			break;
		case 5:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.taxi);
			intent.putExtra("intro", "      上海市官方称作出租车，市民乘车习惯叫做‘打的’，‘打车’，‘差头’，‘拦差头’。（注：“打的”这种说法来自北京，“出租车”发音来自粤港及粤裔华侨众多的东南亚，在上海话中出租车被称为“差头”源自英文‘charter’）" + 
										"上海出租车是城市公共交通的重要组成部分。目前全市有出租车企业261家，从业人员近100,000人，出租汽车近43,000辆，旅游包车和租赁车等8,000多辆。全行业日均客运量近300万人次，约占上海客运总量的24%。");
			break;
		case 6:
			intent.putExtra("title", titles[position]);
			intent.putExtra("imageID", R.drawable.ferry);
			intent.putExtra("intro", "      上海轮渡，简称市轮渡，是上海黄浦江上的一个渡运系统，是城市公共交通系统的组成部分，往来于黄浦江两岸，提供客运和车渡。上海轮渡创始于1911年1月5日[1]，最初为浦东塘工善后局兴办的官营轮渡。在1970年代以前是上海往来黄浦江两岸的唯一方式，1980年代成为全球最繁忙的轮渡航线之一。1990年代随着浦东开发开放和市区段黄浦江大桥的修建，市轮渡的客运压力大幅度缓解，市轮渡逐渐成为欣赏上海浦江两岸风景的最佳工具之一。目前，上海轮渡共经营十八条对江轮渡线。历经百年衍变，现在市轮渡由国营的上海市轮渡有限公司负责日常经营和管理。");
			break;
		}
		startActivity(intent);
	}
	
	
}
