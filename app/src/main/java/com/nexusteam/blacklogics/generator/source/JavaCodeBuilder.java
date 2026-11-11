package com.nexusteam.blacklogics.generator.source;

import android.content.Context;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexusteam.blacklogics.security.crypto.FileEncryptionUtil;
import com.nexusteam.blacklogics.editor.layout.model.LayoutData;
import com.nexusteam.blacklogics.editor.layout.LayoutLoader;
import com.nexusteam.blacklogics.generator.source.model.ActivityStructureRegistry;
import com.nexusteam.blacklogics.logic.ProjectLogicRepository;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.model.DataModel;
import com.nexusteam.blacklogics.sdk.utils.AttributeUtils;
import android.widget.ExpandableListView;
import android.util.Pair;
import com.nexusteam.internal.BlockStorage;
import com.nexusteam.internal.model.ComponentData;
import com.nexusteam.internal.model.VariableData;
import com.nexusteam.internal.beans.BlockBean;
import com.nexusteam.internal.hb;
import com.nexusteam.internal.es;
import com.nexusteam.internal.beans.ComponentBean;
import com.nexusteam.internal.manager.ComponentManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.StringReader;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.regex.Pattern;

public class JavaCodeBuilder {
	
	private static final String[] ACTIONBAR_FIX = {
		"    private void _fixActionBarOverlap() {",
		"        final android.view.View _content = findViewById(android.R.id.content);",
		"        if (_content == null) return;",
		"        _content.post(new Runnable() {",
		"            @Override",
		"            public void run() {",
		"                int _id = getResources().getIdentifier(\"action_bar_container\", \"id\", \"android\");",
		"                android.view.View _bar = findViewById(_id);",
		"                if (_bar == null || _bar.getVisibility() != android.view.View.VISIBLE) return;",
		"                int[] _b = new int[2];",
		"                int[] _c = new int[2];",
		"                _bar.getLocationOnScreen(_b);",
		"                _content.getLocationOnScreen(_c);",
		"                int _overlap = (_b[1] + _bar.getHeight()) - _c[1];",
		"                if (_overlap > 0) {",
		"                    _content.setPadding(_content.getPaddingLeft(), _content.getPaddingTop() + _overlap, _content.getPaddingRight(), _content.getPaddingBottom());",
		"                }",
		"            }",
		"        });",
		"    }",
		""
	};
	
	private static String generateCodeFromBlocks(String activityName, String eventKey) {
		if (eventKey == null || eventKey.isEmpty() || activityName == null) {
			return "";
		}
		
		String blockStorageKey = eventKey + "_" + activityName;
		ArrayList<BlockBean> blocks;
		try {
			blocks = BlockStorage.load(blockStorageKey);
		} catch (Exception e) {
			Log.e("JavaCodeBuilder", "BlockStorage.load failed for key: " + blockStorageKey, e);
			return "";
		}
		
		if (blocks == null || blocks.isEmpty()) {
			return "";
		}
		
		String eventType = eventKey;
		int lastUnderscore = eventKey.lastIndexOf("_");
		if (lastUnderscore != -1 && lastUnderscore < eventKey.length() - 1) {
			eventType = eventKey.substring(lastUnderscore + 1);
		}
		
		try {
			hb generator = new hb(eventType, new es(), activityName, blocks);
			String code = generator.a();
			return code != null ? code : "";
		} catch (Exception e) {
			Log.e("JavaCodeBuilder", "hb code generation failed for key: " + blockStorageKey, e);
			return "";
		}
	}
	
	private static String generateWidgetListenerCode(String activityName, String widgetId, String listenerName) {
		if (widgetId == null || widgetId.isEmpty() || listenerName == null || listenerName.isEmpty()) {
			return "";
		}
		String placeholder = com.nexusteam.blacklogics.utils.EventUtils.getEventPlaceholder(listenerName);
		if (placeholder == null) placeholder = "";
		String hValue = listenerName + " " + placeholder;
		return generateCodeFromBlocks(activityName, widgetId + "_" + hValue);
	}
	
	private static String generateSystemEventCode(String activityName, String eventName) {
		if (eventName == null || eventName.isEmpty()) {
			return "";
		}
		String placeholder = com.nexusteam.blacklogics.utils.EventUtils.getEventPlaceholder(eventName);
		if (placeholder == null) placeholder = "";
		return generateCodeFromBlocks(activityName, eventName + "_" + placeholder);
	}

	/** Package-visible wrapper for FragmentCodeBuilder (no reflection needed). */
	public static String generateSystemEventCodePublic(String activityName, String eventName) {
		return generateSystemEventCode(activityName, eventName);
	}

	/** Context expression for generated code: requireContext() for Fragment, this for Activity. */
	private static String ctxExpr(boolean isFragment) {
		return isFragment ? "requireContext()" : "this";
	}
	
	private static final Pattern ONE_LINE_IF_PATTERN = Pattern.compile("^if\\s*\\(.*\\)\\s*[^\\{;]+;\\s*$");
	private static final Pattern DECREASE_INDENT_PATTERN = Pattern.compile("^(\\}|\\s*else(\\s+if)?|\\s*catch|\\s*finally|\\s*case |\\s*default:)");
	private static final Pattern INCREASE_INDENT_PATTERN = Pattern.compile(".*\\{\\s*$");
	private static final char[] ENC_PASSWORD =
	"blacklogic_layout_secure".toCharArray();
	private static String cachedLayoutJson = null;
	private static long cacheLastModified = -1;
	
	private static String currentScId = null;
	private static Context currentContext = null;
	
	public interface EventBinder {
		void bind(StringBuilder javaCode, String widgetId, String logic);
	}
	
	public static void setCurrentProject(String sc_id, Context context) {
		currentScId = sc_id;
		currentContext = context;
		
		if (context != null && sc_id != null) {
			ActivityTypeRegistry.getInstance().load(context, sc_id);
		}
	}
	
	private static boolean isFabEnabledFor(String activityName) {
		try {
			if (currentScId == null || activityName == null) return false;
			com.nexusteam.blacklogics.development.Complex c =
			new com.nexusteam.blacklogics.development.Complex();
			c.setId(currentScId);
			String name = activityName;
			if (name.endsWith(".java")) name = name.substring(0, name.length() - 5);
			return c.getEnableFabBoolean(name);
		} catch (Throwable t) {
			Log.w("JavaCodeBuilder", "isFabEnabledFor failed", t);
			return false;
		}
	}
	
	public static boolean anyActivityHasCustomToolbarChrome(java.util.List<String> activityNames) {
		if (activityNames == null) return false;
		for (String name : activityNames) {
			if (isDrawerEnabledFor(name)) return true;
		}
		return false;
	}
	
	private static boolean isDrawerEnabledFor(String activityName) {
		try {
			if (currentScId == null || activityName == null) return false;
			com.nexusteam.blacklogics.development.Complex c =
			new com.nexusteam.blacklogics.development.Complex();
			c.setId(currentScId);
			String name = activityName;
			if (name.endsWith(".java")) name = name.substring(0, name.length() - 5);
			return c.isDrawerEnabled(name);
		} catch (Throwable t) {
			Log.w("JavaCodeBuilder", "isDrawerEnabledFor failed", t);
			return false;
		}
	}
	
	private static void appendChromeFields(StringBuilder javaCode, String activityName,
	Map<String, String> widgets) {
		boolean fabOn = isFabEnabledFor(activityName);
		boolean drawerOn = isDrawerEnabledFor(activityName);
		boolean hasToolbar = (widgets != null && (widgets.containsKey("_toolbar") || widgets.containsKey("_app_bar")));
		
		if (!fabOn && !drawerOn && !hasToolbar) return;
		
		if (drawerOn) {
			if (widgets == null || !widgets.containsKey("_drawer")) {
				javaCode.append("    private DrawerLayout _drawer;\n");
			}
			if (widgets == null || !widgets.containsKey("_nav_view")) {
				javaCode.append("    private LinearLayout _nav_view;\n");
			}
		}
		if ((drawerOn || hasToolbar) && (widgets == null || !widgets.containsKey("_toolbar"))) {
			javaCode.append("    private androidx.appcompat.widget.Toolbar _toolbar;\n");
		}
		if (fabOn) {
			if (widgets == null || !widgets.containsKey("_fab")) {
				javaCode.append("    private FloatingActionButton _fab;\n");
			}
		}
	}
	
	private static void appendChromeInit(StringBuilder javaCode, String activityName,
	boolean isFragment) {
		appendChromeInit(javaCode, activityName, isFragment, null);
	}
	
	private static void appendChromeInit(StringBuilder javaCode, String activityName,
	boolean isFragment, String layoutPath) {
		appendChromeInit(javaCode, activityName, isFragment, layoutPath, null);
	}
	
	private static void appendChromeInit(StringBuilder javaCode, String activityName,
	boolean isFragment, String layoutPath, Map<String, String> widgets) {
		boolean fabOn = isFabEnabledFor(activityName);
		boolean drawerOn = isDrawerEnabledFor(activityName);
		boolean hasToolbar = (widgets != null && (widgets.containsKey("_toolbar") || widgets.containsKey("_app_bar")));
		if (!fabOn && !drawerOn && !hasToolbar) return;
		boolean toolbarAlreadyBound = widgets != null && widgets.containsKey("_toolbar");
		
		String root = isFragment ? "rootView." : "";
		String activityRef = isFragment
		? "((androidx.appcompat.app.AppCompatActivity) getActivity())"
		: "this";
		
		if (hasToolbar || drawerOn) {
			if (!toolbarAlreadyBound) {
				javaCode.append("        _toolbar = ").append(root).append("findViewById(R.id._toolbar);\n");
			}
			//javaCode.append("        // Auto: custom Toolbar ko ActionBar banate hain\n");
			javaCode.append("        if (_toolbar != null) {\n");
			javaCode.append("            ").append(activityRef).append(".setSupportActionBar(_toolbar);\n");
			javaCode.append("        }\n");
		}
		
		if (drawerOn) {
			//	javaCode.append("        // Auto: Navigation Drawer\n");
			javaCode.append("        _drawer = ").append(root).append("findViewById(R.id._drawer);\n");
			javaCode.append("        _nav_view = ").append(root).append("findViewById(R.id._nav_view);\n");
			javaCode.append("        // ActionBarDrawerToggle wires hamburger <-> drawer\n");
			javaCode.append("        if (_drawer != null) {\n");
			javaCode.append("            androidx.appcompat.app.ActionBarDrawerToggle _toggle =\n");
			javaCode.append("                new androidx.appcompat.app.ActionBarDrawerToggle(\n");
			javaCode.append("                    ").append(activityRef).append(", _drawer, _toolbar, R.string.app_name, R.string.app_name);\n");
			javaCode.append("            _drawer.addDrawerListener(_toggle);\n");
			javaCode.append("            _toggle.syncState();\n");
			javaCode.append("        }\n");
			appendDrawerWidgetInit(javaCode, activityName, layoutPath);
			javaCode.append("\n");
		}
		
		if (fabOn) {
			//	javaCode.append("        // Auto: FloatingActionButton\n");
			javaCode.append("        _fab = ").append(root).append("findViewById(R.id._fab);\n");
			String fabLogic = generateWidgetListenerCode(activityName, "_fab", "onClick");
			if (fabLogic == null || fabLogic.isEmpty()) {
				fabLogic = generateWidgetListenerCode(activityName, "fab", "onClick");
			}
			javaCode.append("        if (_fab != null) {\n");
			javaCode.append("            _fab.setOnClickListener(new View.OnClickListener() {\n");
			javaCode.append("                @Override\n");
			javaCode.append("                public void onClick(View _view) {\n");
			if (fabLogic != null && !fabLogic.isEmpty()) {
				javaCode.append("                    ").append(fabLogic.replace("\n", "\n                    ")).append("\n");
			}
			javaCode.append("                }\n");
			javaCode.append("            });\n");
			javaCode.append("        }\n\n");
		}
	}
	
	private static String resolveDrawerLayoutResName(String activityName) {
		if (activityName == null || activityName.trim().isEmpty()) return "_drawer_main";
		String n = activityName.trim();
		if (n.endsWith(".java")) n = n.substring(0, n.length() - 5);
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		if (n.startsWith("_drawer_")) return n.toLowerCase();
		if (n.endsWith("Activity") && n.length() > 8) {
			String base = n.substring(0, n.length() - 8);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < base.length(); i++) {
				char c = base.charAt(i);
				if (Character.isUpperCase(c) && i > 0) sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			n = sb.length() > 0 ? sb.toString() : "main";
		} else {
			n = n.toLowerCase();
		}
		if (n.isEmpty()) n = "main";
		return "_drawer_" + n;
	}
	
	private static void appendDrawerWidgetInit(StringBuilder javaCode, String activityName,
	String layoutPath) {
		if (layoutPath == null || layoutPath.isEmpty()) return;
		try {
			String drawerRes = resolveDrawerLayoutResName(activityName);
			Map<String, String> drawerWidgets = extractWidgetsFromLayout(drawerRes, layoutPath);
			if (drawerWidgets == null || drawerWidgets.isEmpty()) {
				drawerWidgets = extractWidgetsFromLayout(drawerRes.toLowerCase(), layoutPath);
			}
			if (drawerWidgets == null || drawerWidgets.isEmpty()) return;
			
			javaCode.append("        // Auto: drawer view ids via _nav_view\n");
			javaCode.append("        if (_nav_view != null) {\n");
			for (Map.Entry<String, String> e : drawerWidgets.entrySet()) {
				String id = e.getKey();
				if (id == null || id.isEmpty()) continue;
				if ("_drawer".equals(id) || "_nav_view".equals(id) || "_drawer_layout".equals(id)
				|| "_fab".equals(id) || "_coordinator".equals(id) || "_toolbar".equals(id)
				|| "_app_bar".equals(id)) {
					continue;
				}
				javaCode.append("            ").append(id)
				.append(" = _nav_view.findViewById(R.id.").append(id).append(");\n");
			}
			javaCode.append("        }\n");
		} catch (Throwable t) {
			Log.w("JavaCodeBuilder", "appendDrawerWidgetInit failed", t);
		}
	}
	
	private static void appendDrawerWidgetFields(StringBuilder javaCode, String activityName,
	String layoutPath, Map<String, String> mainWidgets) {
		if (!isDrawerEnabledFor(activityName) || layoutPath == null) return;
		try {
			String drawerRes = resolveDrawerLayoutResName(activityName);
			Map<String, String> drawerWidgets = extractWidgetsFromLayout(drawerRes, layoutPath);
			if (drawerWidgets == null || drawerWidgets.isEmpty()) return;
			for (Map.Entry<String, String> e : drawerWidgets.entrySet()) {
				String id = e.getKey();
				String type = e.getValue();
				if (id == null || id.isEmpty() || type == null) continue;
				if (mainWidgets != null && mainWidgets.containsKey(id)) continue;
				if ("_drawer".equals(id) || "_nav_view".equals(id) || "_drawer_layout".equals(id)) continue;
				javaCode.append("    private ").append(type).append(" ").append(id).append(";\n");
			}
		} catch (Throwable t) {
			Log.w("JavaCodeBuilder", "appendDrawerWidgetFields failed", t);
		}
	}
	
	private static List<HashMap<String, String>> loadComponentsUnified(String activityName) {
		List<HashMap<String, String>> out = new ArrayList<HashMap<String, String>>();
		if (activityName == null) return out;
		String base = activityName;
		if (base.endsWith(".java")) base = base.substring(0, base.length() - 5);
		String[] keys = new String[] { activityName, base, base + ".java" };
		try {
			for (int i = 0; i < keys.length; i++) {
				if (keys[i] == null) continue;
				ArrayList<ComponentData> list = BlockStorage.loadComponents(keys[i]);
				if (list == null || list.isEmpty()) continue;
				for (int j = 0; j < list.size(); j++) {
					ComponentData d = list.get(j);
					if (d == null || d.mainData == null || d.mainData.length() == 0) continue;
					String typeName = ComponentBean.getComponentTypeName(d.type);
					if (typeName == null || typeName.length() == 0) {
						typeName = "CustomComponent_" + d.type;
					}
					if (d.type == 2) typeName = "SharedPreferences";
					HashMap<String, String> m = new HashMap<String, String>();
					m.put("componentName", typeName);
					m.put("fieldName", d.mainData);
					m.put("extraData", d.extraData != null ? d.extraData : "");
					out.add(m);
				}
				if (!out.isEmpty()) {
					Log.d("JavaCodeBuilder", "components BlockStorage key="
					+ keys[i] + " n=" + out.size());
					return out;
				}
			}
		} catch (Throwable t) {
			Log.w("JavaCodeBuilder", "BlockStorage components", t);
		}
		return out;
	}
	
	private static Map<String, ComponentBean> buildCustomComponentMap() {
		Map<String, ComponentBean> map = new HashMap<String, ComponentBean>();
		for (ComponentBean cb : ComponentManager.getInstance().getCustomComponents()) {
			if (cb.typeName != null && !cb.typeName.isEmpty()) {
				map.put(cb.typeName, cb);
			}
		}
		return map;
	}
	
	private static boolean isCustomResultBased(String componentName, Map<String, ComponentBean> customMap) {
		ComponentBean cb = customMap.get(componentName);
		return cb != null && cb.events != null && !cb.events.isEmpty();
	}
	
	public static void addCustomComponentImports(StringBuilder javaCode, String activityName) {
		List<HashMap<String, String>> components = loadComponentsUnified(activityName);
		if (components == null || components.isEmpty()) return;
		
		Map<String, ComponentBean> customByTypeName = buildCustomComponentMap();
		if (customByTypeName.isEmpty()) return;
		
		Set<String> importSet = new LinkedHashSet<String>();
		
		for (HashMap<String, String> comp : components) {
			String type = comp.get("componentName");
			if (type == null) continue;
			
			ComponentBean cb = customByTypeName.get(type);
			if (cb == null || cb.imports == null || cb.imports.isEmpty()) continue;
			
			String[] importLines = cb.imports.split("[;\\n]");
			for (String rawImport : importLines) {
				String trimmed = rawImport.trim();
				if (trimmed.isEmpty()) continue;
				
				if (!trimmed.startsWith("import ")) {
					trimmed = "import " + trimmed;
				}
				if (!trimmed.endsWith(";")) {
					trimmed = trimmed + ";";
				}
				importSet.add(trimmed);
			}
		}
		
		for (String imp : importSet) {
			javaCode.append(imp).append("\n");
		}
	}
	
	private static void appendBlockLogic(StringBuilder javaCode, String activityName, String eventKey, String indent) {
		String logic = generateCodeFromBlocks(activityName, eventKey);
		if (logic != null && !logic.isEmpty()) {
			javaCode.append(indent).append(logic.replace("\n", "\n" + indent)).append("\n");
		}
	}
	
	public static void addFieldsSection(StringBuilder javaCode, String activityName, String projectPath, String layoutPath) {
		
		if (currentContext != null && currentScId != null) {
			ActivityTypeRegistry.getInstance().load(currentContext, currentScId);
		}
		
		ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
		ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
		
		if (activityType != null && activityType.isFragment()) {
			javaCode.append("    private View rootView;\n");
		}
		
		if (activityType != null && activityType.isDialogFragment()) {
			javaCode.append("    private AlertDialog.Builder alertBuilder;\n");
			javaCode.append("    private AlertDialog alertDialog;\n");
		}
		
		Map<String, String> widgets = extractWidgetsFromLayout(activityName, layoutPath);
		for (Map.Entry<String, String> widget : widgets.entrySet()) {
			javaCode.append("    private ").append(widget.getValue())
			.append(" ").append(widget.getKey()).append(";\n");
		}
		
		appendChromeFields(javaCode, activityName, widgets);
		appendDrawerWidgetFields(javaCode, activityName, layoutPath, widgets);
		
		Object[] cvFieldsEarly = extractWidgetsAndCustomViews(activityName, layoutPath);
		@SuppressWarnings("unchecked")
		Map<String, String> widgetCustomViewsEarly = (Map<String, String>) cvFieldsEarly[1];
		
		
		Object[] cvFields = cvFieldsEarly;
		@SuppressWarnings("unchecked")
		Map<String, String> widgetCustomViews = (Map<String, String>) cvFields[1];
		for (Map.Entry<String, String> cvEntry : widgetCustomViews.entrySet()) {
			String cvWidgetId   = cvEntry.getKey();
			String cvName       = cvEntry.getValue();
			String cvWidgetType = widgets.get(cvWidgetId);
			javaCode.append(generateCustomViewAdapterFields(cvWidgetId, cvWidgetType, cvName));
		}
		
		// Plain ListView / GridView / Spinner (no customView) still need Adapter + List fields
		// so the initialize() code that references them compiles and does not NPE.
		if (widgets != null) {
			for (Map.Entry<String, String> widget : widgets.entrySet()) {
				String wId = widget.getKey();
				String wType = widget.getValue();
				if (wId == null || wType == null) continue;
				if (widgetCustomViews != null && widgetCustomViews.containsKey(wId)) continue;
				if ("ListView".equals(wType) || "GridView".equals(wType) || "Spinner".equals(wType)) {
					javaCode.append("    private ArrayAdapter ").append(wId).append("Adapter;\n");
					javaCode.append("    private ArrayList<String> ").append(wId)
					.append("List = new ArrayList<>();\n");
				}
			}
		}
		
		String javaName = activityName.endsWith(".java") ? activityName : activityName + ".java";
		ArrayList<VariableData> variables = BlockStorage.loadVariables(javaName);
		
		if (variables != null && !variables.isEmpty()) {
			for (VariableData var : variables) {
				String type = getTypeNameFromCode(var.type);
				String name = var.name;
				String value = getInitialValue(type);
				javaCode.append("    private ").append(type).append(" ")
				.append(name).append(" = ").append(value).append(";\n");
			}
		}
		
		List<HashMap<String, String>> components = loadComponentsUnified(activityName);
		if (!components.isEmpty()) {
			boolean timerDeclared = false;
			boolean handlerDeclared = false;
			boolean asynctaskDeclared = false;
			boolean firebaseStorageDeclared = false;
			
			Map<String, ComponentBean> customByTypeName = buildCustomComponentMap();
			
			for (HashMap<String, String> comp : components) {
				if ("FirebaseStorage".equals(comp.get("componentName")) && !firebaseStorageDeclared) {
					javaCode.append("    private FirebaseStorage _firebase_storage;\n");
					firebaseStorageDeclared = true;
				}
			}
			
			for (HashMap<String, String> comp : components) {
				String type = comp.get("componentName");
				String name = comp.get("fieldName");
				if (type == null || type.isEmpty()) {
					continue;
				}
				if ("Unknown".equals(type)) {
					javaCode.append("    // SKIP Unknown component: ").append(name).append("\n");
					continue;
				}
				if (type.startsWith("CustomComponent_")) {
					try {
						int tnum = Integer.parseInt(type.substring("CustomComponent_".length()));
						if (tnum == 22) type = "FragmentAdapter";
						else if (tnum == 23) type = "Handler";
						else if (tnum == 24) type = "Clipboard";
					} catch (Exception ignore) {}
				}
				
				switch (type) {
					case "Timer":
					if (!timerDeclared) {
						javaCode.append("    private Timer _timer = new Timer();\n");
						timerDeclared = true;
					}
					javaCode.append("    private TimerTask ").append(name).append(";\n");
					break;
					
					case "Handler":
					if (!handlerDeclared) {
						javaCode.append("    private Handler _handler = new Handler(Looper.getMainLooper());\n");
						handlerDeclared = true;
					}
					javaCode.append("    private Runnable ").append(name).append(";\n");
					break;
					
					case "AsyncTask":
					if (!asynctaskDeclared) {
						javaCode.append("    private AsyncTask ").append(name).append(";\n");
						asynctaskDeclared = true;
					}
					break;
					
					case "Service":
					javaCode.append("    private Intent ").append(name).append("ServiceIntent;\n");
					break;
					
					case "BroadcastReceiver":
					javaCode.append("    private BroadcastReceiver ").append(name).append(";\n");
					break;
					
					case "ContentProvider":
					javaCode.append("    private ContentResolver ").append(name).append("Resolver;\n");
					break;
					
					case "ViewModel":
					javaCode.append("    private ViewModel ").append(name).append(";\n");
					break;
					
					case "LiveData":
					javaCode.append("    private LiveData ").append(name).append(";\n");
					break;
					
					case "Room":
					javaCode.append("    private AppDatabase ").append(name).append("Database;\n");
					break;
					
					case "WorkManager":
					javaCode.append("    private WorkManager ").append(name).append("WorkManager;\n");
					break;
					
					case "Camera":
					javaCode.append("    public final int REQ_CD_").append(name.toUpperCase())
					.append(" = ").append(105 + components.indexOf(comp)).append(";\n");
					javaCode.append("    private Intent ").append(name)
					.append(" = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);\n");
					javaCode.append("    private File _file_").append(name).append(";\n");
					break;
					
					case "Camera2 API":
					javaCode.append("    private CameraManager ").append(name).append("CameraManager;\n");
					javaCode.append("    private String ").append(name).append("CameraId;\n");
					break;
					
					case "FilePicker":
					javaCode.append("    public final int REQ_CD_").append(name.toUpperCase())
					.append(" = ").append(101 + components.indexOf(comp)).append(";\n");
					javaCode.append("    private Intent ").append(name)
					.append(" = new Intent(Intent.ACTION_GET_CONTENT);\n");
					break;
					
					case "SharedPreferences":
					javaCode.append("    private SharedPreferences ").append(name).append(";\n");
					break;
					
					case "File":
					javaCode.append("    private SharedPreferences ").append(name).append(";\n");
					break;
					
					case "Storage Access Framework":
					javaCode.append("    private static final int ").append(name.toUpperCase()).append("_SAF_REQUEST = 2000;\n");
					break;
					
					case "LocationManager":
					javaCode.append("    private LocationManager ").append(name).append(";\n");
					javaCode.append("    private LocationListener ").append(name).append("Listener;\n");
					break;
					
					case "FragmentAdapter": {
						String _fa = name.substring(0, 1).toUpperCase() + (name.length() > 1 ? name.substring(1) : "");
						javaCode.append("    private ").append(_fa).append("FragmentAdapter ").append(name).append(";\n");
						break;
					}
					
					case "Clipboard":
					javaCode.append("    private ClipboardManager ").append(name).append(";\n");
					break;
					
					case "SensorManager":
					javaCode.append("    private SensorManager ").append(name).append(";\n");
					javaCode.append("    private Sensor ").append(name).append("Sensor;\n");
					break;
					
					case "BluetoothAdapter":
					javaCode.append("    private BluetoothAdapter ").append(name).append(";\n");
					break;
					
					case "AlarmManager":
					javaCode.append("    private AlarmManager ").append(name).append(";\n");
					break;
					
					case "JobScheduler":
					javaCode.append("    private JobScheduler ").append(name).append(";\n");
					break;
					
					case "ConnectivityManager":
					javaCode.append("    private ConnectivityManager ").append(name).append(";\n");
					break;
					
					case "PowerManager":
					javaCode.append("    private PowerManager ").append(name).append(";\n");
					javaCode.append("    private PowerManager.WakeLock ").append(name).append("WakeLock;\n");
					break;
					
					case "ClipboardManager":
					javaCode.append("    private ClipboardManager ").append(name).append(";\n");
					break;
					
					case "PackageManager":
					javaCode.append("    private PackageManager ").append(name).append(";\n");
					break;
					
					case "DownloadManager":
					javaCode.append("    private DownloadManager ").append(name).append(";\n");
					break;
					
					case "Vibrator":
					javaCode.append("    private Vibrator ").append(name).append(";\n");
					break;
					
					case "AudioManager":
					javaCode.append("    private AudioManager ").append(name).append(";\n");
					break;
					
					case "AccountManager":
					javaCode.append("    private AccountManager ").append(name).append(";\n");
					break;
					
					case "TelephonyManager":
					javaCode.append("    private TelephonyManager ").append(name).append(";\n");
					break;
					
					case "InputMethodManager":
					javaCode.append("    private InputMethodManager ").append(name).append(";\n");
					break;
					
					case "WindowManager":
					javaCode.append("    private WindowManager ").append(name).append(";\n");
					break;
					
					case "ActivityManager":
					javaCode.append("    private ActivityManager ").append(name).append(";\n");
					break;
					
					case "AppOpsManager":
					javaCode.append("    private AppOpsManager ").append(name).append(";\n");
					break;
					
					case "AlertDialog.Builder":
					javaCode.append("    private AlertDialog.Builder ").append(name).append(";\n");
					break;
					
					case "BottomSheetDialog":
					javaCode.append("    private BottomSheetDialog ").append(name).append(";\n");
					break;
					
					case "ProgressDialog":
					javaCode.append("    private ProgressDialog ").append(name).append(";\n");
					break;
					
					case "DatePickerDialog":
					javaCode.append("    private DatePickerDialog ").append(name).append(";\n");
					break;
					
					case "TimePickerDialog":
					javaCode.append("    private TimePickerDialog ").append(name).append(";\n");
					break;
					
					case "ArrayAdapter":
					javaCode.append("    private ArrayAdapter ").append(name).append(";\n");
					break;
					
					case "AlertDialog":
					javaCode.append("    private AlertDialog ").append(name).append(";\n");
					break;
					
					case "Dialog":
					javaCode.append("    private AlertDialog.Builder ").append(name).append(";\n");
					break;
					
					case "RequestNetwork":
					javaCode.append("    private RequestNetwork ").append(name).append(";\n");
					javaCode.append("    private RequestNetwork.RequestListener _").append(name).append("_request_listener;\n");
					break;
					
					case "BluetoothConnect":
					javaCode.append("    private BluetoothConnect ").append(name).append(";\n");
					javaCode.append("    private BluetoothConnect.BluetoothConnectionListener _").append(name).append("_bluetooth_connection_listener;\n");
					break;
					
					case "InterstitialAd":
					javaCode.append("    private InterstitialAd ").append(name).append(";\n");
					javaCode.append("    private AdListener _").append(name).append("_ad_listener;\n");
					break;
					
					case "FirebaseDB":
					javaCode.append("    private DatabaseReference ").append(name).append(";\n");
					javaCode.append("    private ChildEventListener _").append(name).append("_child_listener;\n");
					break;
					
					case "FirebaseAuth":
					javaCode.append("    private FirebaseAuth ").append(name).append(";\n");
					javaCode.append("    private OnCompleteListener<AuthResult> _").append(name).append("_create_user_listener;\n");
					javaCode.append("    private OnCompleteListener<AuthResult> _").append(name).append("_sign_in_listener;\n");
					javaCode.append("    private OnCompleteListener<Void> _").append(name).append("_reset_password_listener;\n");
					break;
					
					case "Gyroscope":
					javaCode.append("    private SensorManager ").append(name).append(";\n");
					javaCode.append("    private SensorEventListener _").append(name).append("_sensor_listener;\n");
					break;
					
					case "FirebaseStorage":
					javaCode.append("    private StorageReference ").append(name).append(";\n");
					javaCode.append("    private OnSuccessListener<UploadTask.TaskSnapshot> _").append(name).append("_upload_success_listener;\n");
					javaCode.append("    private OnProgressListener<UploadTask.TaskSnapshot> _").append(name).append("_upload_progress_listener;\n");
					javaCode.append("    private OnSuccessListener<FileDownloadTask.TaskSnapshot> _").append(name).append("_download_success_listener;\n");
					javaCode.append("    private OnProgressListener<FileDownloadTask.TaskSnapshot> _").append(name).append("_download_progress_listener;\n");
					javaCode.append("    private OnSuccessListener<Void> _").append(name).append("_delete_success_listener;\n");
					javaCode.append("    private OnFailureListener _").append(name).append("_failure_listener;\n");
					break;
					
					default:
					if (type == null || type.length() == 0 || "Unknown".equals(type)) {
						javaCode.append("    // SKIP Unknown component ").append(name).append(" — delete and re-add FragmentAdapter\n");
					} else {
						javaCode.append("    private ").append(type).append(" ").append(name).append(";\n");
					}
					if (isCustomResultBased(type, customByTypeName)) {
						javaCode.append("    public final int ").append(name.toUpperCase()).append("_REQUEST = ").append(1000 + components.indexOf(comp)).append(";\n");
					}
					break;
				}
			}
		}
		
		javaCode.append("\n");
	}
	
	private static boolean isAndroidXOn() {
		try {
			if (currentScId == null) return true;
			com.nexusteam.blacklogics.development.Complex c =
			new com.nexusteam.blacklogics.development.Complex();
			c.setId(currentScId);
			return c.getAndroidXEnable();
		} catch (Throwable t) {
			return true;
		}
	}
	
	public static void addOnCreateMethod(StringBuilder javaCode, String activityName, String layoutName) {
		if (currentContext != null && currentScId != null) {
			ActivityTypeRegistry.getInstance().load(currentContext, currentScId);
		}
		ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
		ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
		
		boolean hasFilePicker = false;
		boolean hasCamera = false;
		List<HashMap<String, String>> components = loadComponentsUnified(activityName);
		if (components != null) {
			for (HashMap<String, String> comp : components) {
				String cn = comp.get("componentName");
				if ("FilePicker".equals(cn)) hasFilePicker = true;
				if ("Camera".equals(cn) || "Camera2 API".equals(cn)) hasCamera = true;
			}
		}
		
		if (activityType != null && activityType.isFragment()) {
			// Fragment onCreateView / onViewCreated / dialog shell is owned by FragmentCodeBuilder.
			// Do not emit here to avoid duplicate methods.
			return;
		} else {
			
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
			javaCode.append("        super.onCreate(savedInstanceState);\n");
			javaCode.append("        setContentView(R.layout.").append(layoutName).append(");\n");
			if (!isAndroidXOn()) {
				javaCode.append("        _fixActionBarOverlap();\n");
			}
			javaCode.append("        initialize(savedInstanceState);\n");
			
			if (hasFilePicker || hasCamera) {
				javaCode.append("        if (android.os.Build.VERSION.SDK_INT >= 23) {\n");
				javaCode.append("            java.util.ArrayList<String> _perms = new java.util.ArrayList<String>();\n");
				if (hasCamera) {
					javaCode.append("            if (checkSelfPermission(android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) _perms.add(android.Manifest.permission.CAMERA);\n");
				}
				if (hasFilePicker) {
					javaCode.append("            if (android.os.Build.VERSION.SDK_INT >= 33 && getApplicationInfo().targetSdkVersion >= 33) {\n");
					javaCode.append("                String[] _media = {\"android.permission.READ_MEDIA_IMAGES\", \"android.permission.READ_MEDIA_VIDEO\", \"android.permission.READ_MEDIA_AUDIO\"};\n");
					javaCode.append("                for (String _m : _media) {\n");
					javaCode.append("                    if (checkSelfPermission(_m) != android.content.pm.PackageManager.PERMISSION_GRANTED) _perms.add(_m);\n");
					javaCode.append("                }\n");
					javaCode.append("            } else {\n");
					javaCode.append("                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) _perms.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);\n");
					javaCode.append("                if (android.os.Build.VERSION.SDK_INT < 30 && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) _perms.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);\n");
					javaCode.append("            }\n");
				}
				javaCode.append("            if (_perms.isEmpty()) {\n");
				javaCode.append("                _afterPermissions();\n");
				javaCode.append("            } else {\n");
				javaCode.append("                android.util.Log.d(\"PermCheck\", \"requesting: \" + _perms + \" targetSdk=\" + getApplicationInfo().targetSdkVersion);\n");
				javaCode.append("                requestPermissions(_perms.toArray(new String[0]), 1000);\n");
				javaCode.append("            }\n");
				javaCode.append("        } else {\n");
				javaCode.append("            _afterPermissions();\n");
				javaCode.append("        }\n");
			} else {
				javaCode.append("        initializeLogic();\n");
			}
			
			javaCode.append("    }\n\n");
			
			if (!isAndroidXOn()) {
				for (String l : ACTIONBAR_FIX) javaCode.append(l).append("\n");
			}
		}
	}
	
	public static void addInitializeMethod(StringBuilder javaCode, String activityName, String projectPath, String layoutPath) {
		ActivityTypeRegistry registry = ActivityTypeRegistry.getInstance();
		com.nexusteam.blacklogics.generator.source.model.ActivityConfig config = registry.getActivityConfig(activityName);
		ActivityType activityType = (config != null) ? config.getType() : ActivityType.ACTIVITY;
		boolean isFragment = (activityType != null && activityType.isFragment());
		
		javaCode.append("    private void initialize(Bundle savedInstanceState) {\n");
		
		boolean needsTimer = false;
		boolean needsHandler = false;
		boolean needsTimerTask = false;
		boolean needsNotificationManager = false;
		boolean needsCameraManager = false;
		Set<String> declaredSharedPrefs = new HashSet<>();
		boolean needsFilePicker = false;
		
		Map<String, String> widgets = extractWidgetsFromLayout(activityName, layoutPath);
		if (widgets != null && !widgets.isEmpty()) {
			if (isFragment) {
				javaCode.append("        if (rootView == null) return;\n\n");
				for (String widgetId : widgets.keySet()) {
					javaCode.append("        ").append(widgetId)
					.append(" = rootView.findViewById(R.id.").append(widgetId).append(");\n");
				}
			} else {
				for (String widgetId : widgets.keySet()) {
					javaCode.append("        ").append(widgetId)
					.append(" = findViewById(R.id.").append(widgetId).append(");\n");
				}
			}
			javaCode.append("\n");
		}
		
		appendChromeInit(javaCode, activityName, isFragment, layoutPath, widgets);
		
		Object[] cvInitEarly = extractWidgetsAndCustomViews(activityName, layoutPath);
		@SuppressWarnings("unchecked")
		Map<String, String> cvInitMapEarly = (Map<String, String>) cvInitEarly[1];
		
		if (widgets != null) {
			for (Map.Entry<String, String> widget : widgets.entrySet()) {
				if ("ListView".equals(widget.getValue())) {
					String lvId = widget.getKey();
					if (cvInitMapEarly.containsKey(lvId)) {
						continue;
					}
					javaCode.append("        ").append(lvId).append("Adapter = new ArrayAdapter<>(").append(ctxExpr(isFragment)).append(", android.R.layout.simple_list_item_1, ").append(lvId).append("List);\n");
					javaCode.append("        ").append(lvId).append(".setAdapter(").append(lvId).append("Adapter);\n");
					javaCode.append("        ").append(lvId).append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onItemClick(AdapterView<?> _adView, View _v, int _position, long _id) {\n");
					String lvLogic = generateWidgetListenerCode(activityName, lvId, "onItemClick");
					if (lvLogic != null && !lvLogic.isEmpty()) {
						javaCode.append("                ").append(lvLogic.replace("\n", "\n                ")).append("\n");
					}
					javaCode.append("            }\n");
					javaCode.append("        });\n\n");
				}
			}
		}
		
		StringBuilder classDefs = new StringBuilder();
		
		Object[] cvInit = cvInitEarly;
		@SuppressWarnings("unchecked")
		Map<String, String> cvInitMap = (Map<String, String>) cvInit[1];
		for (Map.Entry<String, String> cvEntry : cvInitMap.entrySet()) {
			String cvWidgetId   = cvEntry.getKey();
			String cvName       = cvEntry.getValue();
			String cvWidgetType = (widgets != null) ? widgets.get(cvWidgetId) : null;
			generateCustomViewAdapterInit(cvWidgetId, cvWidgetType, cvName, activityName, layoutPath, javaCode, classDefs);
		}
		
		generateFragmentAdapterClasses(activityName, classDefs);
		
		List<HashMap<String, String>> components = loadComponentsUnified(activityName);
		if (components != null && !components.isEmpty()) {
			
			for (HashMap<String, String> comp : components) {
				String type = comp.get("componentName");
				if (type == null) continue;
				
				switch (type) {
					case "Timer":
					needsTimer = true;
					break;
					case "Handler":
					needsHandler = true;
					break;
					case "Notification":
					needsNotificationManager = true;
					break;
					case "Camera2 API":
					needsCameraManager = true;
					break;
				}
			}
			
			if (needsTimer) {
				javaCode.append("        if (_timer == null) {\n");
				javaCode.append("            _timer = new Timer();\n");
				javaCode.append("        }\n");
			}
			if (needsHandler) {
				javaCode.append("        if (_handler == null) {\n");
				javaCode.append("            _handler = new Handler(Looper.getMainLooper());\n");
				javaCode.append("        }\n");
			}
			if (needsNotificationManager) {
				javaCode.append("        if (_notificationManager == null) {\n");
				javaCode.append("            _notificationManager = (NotificationManager) ").append(ctxExpr(isFragment)).append(".getSystemService(Context.NOTIFICATION_SERVICE);\n");
				javaCode.append("        }\n");
			}
			if (needsCameraManager) {
				javaCode.append("        if (_cameraManager == null) {\n");
				javaCode.append("            _cameraManager = (CameraManager) ").append(ctxExpr(isFragment)).append(".getSystemService(Context.CAMERA_SERVICE);\n");
				javaCode.append("        }\n");
			}
			
			if (needsTimer || needsHandler || needsNotificationManager || needsCameraManager) {
				javaCode.append("\n");
			}
			
			for (HashMap<String, String> comp : components) {
				String type = comp.get("componentName");
				String name = comp.get("fieldName");
				String extraData = comp.get("extraData");
				
				if (type == null || name == null) continue;
				
				String logic = generateCodeFromBlocks(activityName, name);
				
				switch (type) {
					case "Intent":
					javaCode.append("        ").append(name).append(" = new Intent();\n");
					break;
					
					case "Dialog":
					javaCode.append("        ").append(name).append(" = new AlertDialog.Builder(").append(ctxExpr(isFragment)).append(");\n");
					break;
					
					case "ObjectAnimator": {
						javaCode.append("        ").append(name).append(" = new ObjectAnimator();\n");
						
						String animStartLogic  = generateCodeFromBlocks(activityName, name + "_onAnimationStart");
						String animEndLogic    = generateCodeFromBlocks(activityName, name + "_onAnimationEnd");
						String animCancelLogic = generateCodeFromBlocks(activityName, name + "_onAnimationCancel");
						String animRepeatLogic = generateCodeFromBlocks(activityName, name + "_onAnimationRepeat");
						
						javaCode.append("        ").append(name).append(".addListener(new Animator.AnimatorListener() {\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onAnimationStart(Animator _animation) {\n");
						if (animStartLogic != null && !animStartLogic.isEmpty()) {
							javaCode.append("                ").append(animStartLogic.replace("\n", "\n                ")).append("\n");
						}
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onAnimationEnd(Animator _animation) {\n");
						if (animEndLogic != null && !animEndLogic.isEmpty()) {
							javaCode.append("                ").append(animEndLogic.replace("\n", "\n                ")).append("\n");
						}
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onAnimationCancel(Animator _animation) {\n");
						if (animCancelLogic != null && !animCancelLogic.isEmpty()) {
							javaCode.append("                ").append(animCancelLogic.replace("\n", "\n                ")).append("\n");
						}
						javaCode.append("            }\n");
						javaCode.append("            @Override\n");
						javaCode.append("            public void onAnimationRepeat(Animator _animation) {\n");
						if (animRepeatLogic != null && !animRepeatLogic.isEmpty()) {
							javaCode.append("                ").append(animRepeatLogic.replace("\n", "\n                ")).append("\n");
						}
						javaCode.append("            }\n");
						javaCode.append("        });\n");
						break;
					}
					
					case "Calendar":
					javaCode.append("        ").append(name).append(" = Calendar.getInstance();\n");
					break;
					
					case "Timer":
					needsTimerTask = true;
					javaCode.append("        ").append(name).append(" = new TimerTask() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void run() {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                ").append(logic.replace("\n", "\n                ")).append("\n");
					}
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _timer.schedule(").append(name).append(", 0, 1000);\n");
					break;
					
					case "Notification":
					String notifName = name;
					javaCode.append("        NotificationChannel ").append(notifName).append("Channel = new NotificationChannel(\n");
					javaCode.append("                \"default_channel\", \n");
					javaCode.append("                \"Default Channel\", \n");
					javaCode.append("                NotificationManager.IMPORTANCE_DEFAULT\n");
					javaCode.append("        );\n");
					javaCode.append("        _notificationManager.createNotificationChannel(").append(notifName).append("Channel);\n");
					javaCode.append("        Notification.Builder ").append(notifName).append("Builder = new Notification.Builder(this, \"default_channel\")\n");
					javaCode.append("                .setContentTitle(\"Notification Title\")\n");
					javaCode.append("                .setContentText(\"Notification Message\")\n");
					javaCode.append("                .setSmallIcon(android.R.drawable.ic_dialog_info);\n");
					javaCode.append("        ").append(notifName).append(" = ").append(notifName).append("Builder.build();\n");
					break;
					
					case "AsyncTask":
					javaCode.append("        ").append(name).append(" = new AsyncTask<Void, Void, Void>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            protected Void doInBackground(Void... params) {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                ").append(logic.replace("\n", "\n                ")).append("\n");
					}
					javaCode.append("                return null;\n");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "Handler":
					javaCode.append("        ").append(name).append(" = new Runnable() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void run() {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                ").append(logic.replace("\n", "\n                ")).append("\n");
					}
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _handler.post(").append(name).append(");\n");
					break;
					
					case "Service":
					String serviceClass = extraData != null && !extraData.isEmpty() ? extraData : "MyService";
					javaCode.append("        ").append(name).append("Intent = new Intent(this, ").append(serviceClass).append(".class);\n");
					break;
					
					case "BroadcastReceiver":
					javaCode.append("        ").append(name).append(" = new BroadcastReceiver() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onReceive(Context context, Intent intent) {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                ").append(logic.replace("\n", "\n                ")).append("\n");
					}
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        // Don't forget to register this receiver in onResume() and unregister in onPause()\n");
					break;
					
					case "ContentProvider":
					javaCode.append("        ").append(name).append("Resolver = getContentResolver();\n");
					break;
					
					case "ViewModel":
					javaCode.append("        ").append(name).append(" = new ViewModelProvider(this).get(MyViewModel.class);\n");
					break;
					
					case "LiveData":
					javaCode.append("        ").append(name).append(" = new MutableLiveData<>();\n");
					break;
					
					case "Room":
					String dbName = extraData != null && !extraData.isEmpty() ? extraData : "app-database";
					javaCode.append("        ").append(name).append("Database = Room.databaseBuilder(\n");
					javaCode.append("                getApplicationContext(),\n");
					javaCode.append("                AppDatabase.class,\n");
					javaCode.append("                \"").append(dbName).append("\"\n");
					javaCode.append("        ).build();\n");
					break;
					
					case "WorkManager":
					javaCode.append("        ").append(name).append("WorkManager = WorkManager.getInstance(this);\n");
					break;
					
					case "MediaPlayer":
					javaCode.append("        ").append(name).append(" = new MediaPlayer();\n");
					break;
					
					case "Camera":
					javaCode.append("        _file_").append(name)
					.append(" = FileUtil.createNewPictureFile(getApplicationContext());\n");
					javaCode.append("        Uri _uri_").append(name).append(";\n");
					if (isAndroidXOn()) {
						javaCode.append("        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {\n");
						javaCode.append("            _uri_").append(name)
						.append(" = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + \".provider\", _file_")
						.append(name).append(");\n");
						javaCode.append("        } else {\n");
						javaCode.append("            _uri_").append(name).append(" = Uri.fromFile(_file_")
						.append(name).append(");\n");
						javaCode.append("        }\n");
					} else {
						javaCode.append("        android.os.StrictMode.setVmPolicy(new android.os.StrictMode.VmPolicy.Builder().build());\n");
						javaCode.append("        _uri_").append(name).append(" = Uri.fromFile(_file_")
						.append(name).append(");\n");
					}
					javaCode.append("        ").append(name)
					.append(".putExtra(MediaStore.EXTRA_OUTPUT, _uri_").append(name).append(");\n");
					javaCode.append("        ").append(name)
					.append(".addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);\n");
					break;
					
					case "Camera2 API":
					String cameraName = name;
					javaCode.append("        try {\n");
					javaCode.append("            String[] cameraIdList = _cameraManager.getCameraIdList();\n");
					javaCode.append("            if (cameraIdList.length > 0) {\n");
					javaCode.append("                ").append(cameraName).append("CameraId = cameraIdList[0];\n");
					javaCode.append("            }\n");
					javaCode.append("        } catch (Exception e) {\n");
					javaCode.append("            e.printStackTrace();\n");
					javaCode.append("        }\n");
					break;
					
					case "LocationManager":
					javaCode.append("        ").append(name).append(" = (LocationManager) getSystemService(Context.LOCATION_SERVICE);\n");
					javaCode.append("        ").append(name).append("Listener = new LocationListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onLocationChanged(Location location) {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                ").append(logic.replace("\n", "\n                ")).append("\n");
					}
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onStatusChanged(String provider, int status, Bundle extras) {}\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onProviderEnabled(String provider) {}\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onProviderDisabled(String provider) {}\n");
					javaCode.append("        };\n");
					break;
					
					case "FragmentAdapter": {
						String _fa = name.substring(0, 1).toUpperCase() + (name.length() > 1 ? name.substring(1) : "");
						javaCode.append("        ").append(name).append(" = new ").append(_fa).append("FragmentAdapter(getSupportFragmentManager());\n");
						break;
					}
					
					case "Clipboard":
					javaCode.append("        ").append(name).append(" = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);\n");
					break;
					
					case "SensorManager":
					javaCode.append("        ").append(name).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
					javaCode.append("        ").append(name).append("Sensor = ").append(name).append(".getDefaultSensor(Sensor.TYPE_ACCELEROMETER);\n");
					break;
					
					case "BluetoothAdapter":
					javaCode.append("        ").append(name).append(" = BluetoothAdapter.getDefaultAdapter();\n");
					break;
					
					case "AlarmManager":
					javaCode.append("        ").append(name).append(" = (AlarmManager) getSystemService(Context.ALARM_SERVICE);\n");
					break;
					
					case "JobScheduler":
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
						javaCode.append("        ").append(name).append(" = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);\n");
					}
					break;
					
					case "ConnectivityManager":
					javaCode.append("        ").append(name).append(" = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);\n");
					break;
					
					case "PowerManager":
					javaCode.append("        ").append(name).append(" = (PowerManager) getSystemService(Context.POWER_SERVICE);\n");
					javaCode.append("        ").append(name).append("WakeLock = ").append(name).append(".newWakeLock(\n");
					javaCode.append("                PowerManager.PARTIAL_WAKE_LOCK,\n");
					javaCode.append("                \"").append(name).append(":wakelocktag\"\n");
					javaCode.append("        );\n");
					break;
					
					case "ClipboardManager":
					javaCode.append("        ").append(name).append(" = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);\n");
					break;
					
					case "PackageManager":
					javaCode.append("        ").append(name).append(" = getPackageManager();\n");
					break;
					
					case "DownloadManager":
					javaCode.append("        ").append(name).append(" = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);\n");
					break;
					
					case "Vibrator":
					javaCode.append("        ").append(name).append(" = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);\n");
					break;
					
					case "AudioManager":
					javaCode.append("        ").append(name).append(" = (AudioManager) getSystemService(Context.AUDIO_SERVICE);\n");
					break;
					
					case "AccountManager":
					javaCode.append("        ").append(name).append(" = AccountManager.get(this);\n");
					break;
					
					case "TelephonyManager":
					javaCode.append("        ").append(name).append(" = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);\n");
					break;
					
					case "InputMethodManager":
					javaCode.append("        ").append(name).append(" = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);\n");
					break;
					
					case "WindowManager":
					javaCode.append("        ").append(name).append(" = (WindowManager) getSystemService(Context.WINDOW_SERVICE);\n");
					break;
					
					case "ActivityManager":
					javaCode.append("        ").append(name).append(" = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);\n");
					break;
					
					case "AppOpsManager":
					if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
						javaCode.append("        ").append(name).append(" = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);\n");
					}
					break;
					
					case "AlertDialog.Builder":
					javaCode.append("        ").append(name).append(" = new AlertDialog.Builder(").append(ctxExpr(isFragment)).append(");\n");
					break;
					
					case "BottomSheetDialog":
					javaCode.append("        ").append(name).append(" = new BottomSheetDialog(this);\n");
					break;
					
					case "ProgressDialog":
					javaCode.append("        ").append(name).append(" = new ProgressDialog(this);\n");
					javaCode.append("        ").append(name).append(".setMessage(\"Loading...\");\n");
					javaCode.append("        ").append(name).append(".setCancelable(false);\n");
					break;
					
					case "DatePickerDialog":
					javaCode.append("        Calendar ").append(name).append("Calendar = Calendar.getInstance();\n");
					javaCode.append("        ").append(name).append(" = new DatePickerDialog(this,\n");
					javaCode.append("                new DatePickerDialog.OnDateSetListener() {\n");
					javaCode.append("                    @Override\n");
					javaCode.append("                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                        ").append(logic.replace("\n", "\n                        ")).append("\n");
					}
					javaCode.append("                    }\n");
					javaCode.append("                },\n");
					javaCode.append("                ").append(name).append("Calendar.get(Calendar.YEAR),\n");
					javaCode.append("                ").append(name).append("Calendar.get(Calendar.MONTH),\n");
					javaCode.append("                ").append(name).append("Calendar.get(Calendar.DAY_OF_MONTH));\n");
					break;
					
					case "TimePickerDialog":
					javaCode.append("        Calendar ").append(name).append("Calendar = Calendar.getInstance();\n");
					javaCode.append("        ").append(name).append(" = new TimePickerDialog(this,\n");
					javaCode.append("                new TimePickerDialog.OnTimeSetListener() {\n");
					javaCode.append("                    @Override\n");
					javaCode.append("                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {\n");
					if (logic != null && !logic.isEmpty()) {
						javaCode.append("                        ").append(logic.replace("\n", "\n                        ")).append("\n");
					}
					javaCode.append("                    }\n");
					javaCode.append("                },\n");
					javaCode.append("                ").append(name).append("Calendar.get(Calendar.HOUR_OF_DAY),\n");
					javaCode.append("                ").append(name).append("Calendar.get(Calendar.MINUTE),\n");
					javaCode.append("                true);\n");
					break;
					
					case "ArrayAdapter":
					javaCode.append("        ").append(name).append(" = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);\n");
					break;
					
					case "FilePicker":
					{
						String mimeType = (extraData != null && !extraData.trim().isEmpty()) ? extraData.trim() : "*/*";
						needsFilePicker = true;
						javaCode.append("        ").append(name).append(".setType(\"").append(mimeType).append("\");\n");
						javaCode.append("        ").append(name).append(".putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);\n");
					}
					break;
					
					case "SharedPreferences":
					String prefName = extraData != null && !extraData.trim().isEmpty() ? extraData : "app_prefs";
					if (!declaredSharedPrefs.contains(prefName)) {
						declaredSharedPrefs.add(prefName);
						javaCode.append("        ").append(name).append(" = ").append(ctxExpr(isFragment)).append(".getSharedPreferences(\"").append(prefName).append("\", Context.MODE_PRIVATE);\n");
					}
					break;
					case "File":
					String prefname = extraData != null && !extraData.trim().isEmpty() ? extraData : "app_prefs";
					if (!declaredSharedPrefs.contains(prefname)) {
						declaredSharedPrefs.add(prefname);
						javaCode.append("        ").append(name).append(" = ").append(ctxExpr(isFragment)).append(".getSharedPreferences(\"").append(prefname).append("\", Context.MODE_PRIVATE);\n");
					}
					break;
					
					case "AlertDialog":
					javaCode.append("        AlertDialog.Builder ").append(name).append("Builder = new AlertDialog.Builder(").append(ctxExpr(isFragment)).append(");\n");
					break;
					
					case "RequestNetwork":
					javaCode.append("        ").append(name).append(" = new RequestNetwork(").append(ctxExpr(isFragment)).append(");\n");
					javaCode.append("        _").append(name).append("_request_listener = new RequestNetwork.RequestListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onResponse", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onErrorResponse(String tag, String message) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onErrorResponse", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "BluetoothConnect":
					javaCode.append("        ").append(name).append(" = new BluetoothConnect(").append(ctxExpr(isFragment)).append(");\n");
					javaCode.append("        _").append(name).append("_bluetooth_connection_listener = new BluetoothConnect.BluetoothConnectionListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onConnected(String tag, HashMap<String, Object> deviceData) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onConnected", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onDataReceived(String tag, byte[] data, int bytes) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onDataReceived", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onDataSent(String tag, byte[] data) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onDataSent", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onConnectionError(String tag, String connectionState, String message) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onConnectionError", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onConnectionStopped(String tag) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onConnectionStopped", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "InterstitialAd":
					javaCode.append("        _").append(name).append("_ad_listener = new AdListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onAdLoaded() {\n");
					appendBlockLogic(javaCode, activityName, name + "_onAdLoaded", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onAdFailedToLoad(LoadAdError adError) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onAdFailedToLoad", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onAdOpened() {\n");
					appendBlockLogic(javaCode, activityName, name + "_onAdOpened", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onAdClosed() {\n");
					appendBlockLogic(javaCode, activityName, name + "_onAdClosed", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "FirebaseDB":
					{
						String dbPath = (extraData != null && !extraData.trim().isEmpty()) ? extraData.trim() : name;
						javaCode.append("        ").append(name).append(" = FirebaseDatabase.getInstance().getReference(\"").append(dbPath).append("\");\n");
					}
					javaCode.append("        _").append(name).append("_child_listener = new ChildEventListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onChildAdded", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onChildChanged", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onChildRemoved(DataSnapshot snapshot) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onChildRemoved", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {\n");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onCancelled(DatabaseError error) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onCancelled", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "FirebaseAuth":
					javaCode.append("        ").append(name).append(" = FirebaseAuth.getInstance();\n");
					javaCode.append("        _").append(name).append("_create_user_listener = new OnCompleteListener<AuthResult>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onComplete(Task<AuthResult> task) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onCreateUserComplete", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_sign_in_listener = new OnCompleteListener<AuthResult>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onComplete(Task<AuthResult> task) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onSignInUserComplete", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_reset_password_listener = new OnCompleteListener<Void>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onComplete(Task<Void> task) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onResetPasswordEmailSent", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "Gyroscope":
					javaCode.append("        ").append(name).append(" = (SensorManager) getSystemService(Context.SENSOR_SERVICE);\n");
					javaCode.append("        _").append(name).append("_sensor_listener = new SensorEventListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onSensorChanged(SensorEvent event) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onSensorChanged", "                ");
					javaCode.append("            }\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onAccuracyChanged(Sensor sensor, int accuracy) {\n");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					case "FirebaseStorage":
					javaCode.append("        if (_firebase_storage == null) {\n");
					javaCode.append("            _firebase_storage = FirebaseStorage.getInstance();\n");
					javaCode.append("        }\n");
					javaCode.append("        ").append(name).append(" = _firebase_storage.getReference();\n");
					javaCode.append("        _").append(name).append("_upload_success_listener = new OnSuccessListener<UploadTask.TaskSnapshot>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onSuccess(UploadTask.TaskSnapshot snapshot) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onUploadSuccess", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onProgress(UploadTask.TaskSnapshot snapshot) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onUploadProgress", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onSuccess(FileDownloadTask.TaskSnapshot snapshot) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onDownloadSuccess", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onProgress(FileDownloadTask.TaskSnapshot snapshot) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onDownloadProgress", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_delete_success_listener = new OnSuccessListener<Void>() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onSuccess(Void result) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onDeleteSuccess", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					javaCode.append("        _").append(name).append("_failure_listener = new OnFailureListener() {\n");
					javaCode.append("            @Override\n");
					javaCode.append("            public void onFailure(Exception e) {\n");
					appendBlockLogic(javaCode, activityName, name + "_onFailure", "                ");
					javaCode.append("            }\n");
					javaCode.append("        };\n");
					break;
					
					default:
					System.err.println("Warning: Unknown component type: " + type);
					break;
				}
			}
		}
		
		addWidgetEventBindings(javaCode, activityName, layoutPath);
		
		javaCode.append("    }\n\n");
		
		if (classDefs.length() > 0) {
			javaCode.append(classDefs);
		}
		
		if (needsTimer || needsHandler) {
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onDestroy() {\n");
			javaCode.append("        super.onDestroy();\n");
			if (needsTimer) {
				javaCode.append("        if (_timer != null) {\n");
				javaCode.append("            _timer.cancel();\n");
				javaCode.append("            _timer = null;\n");
				javaCode.append("        }\n");
			}
			if (needsHandler) {
				javaCode.append("        if (_handler != null) {\n");
				javaCode.append("            _handler.removeCallbacksAndMessages(null);\n");
				javaCode.append("            _handler = null;\n");
				javaCode.append("        }\n");
			}
			javaCode.append("    }\n\n");
		}
	}
	
	public static void addInitializeLogicMethod(StringBuilder javaCode, String activityName, String projectPath) {
		
		String mainLogic = generateCodeFromBlocks(activityName, "onCreate_initializeLogic");
		StringBuilder formattedLogic = new StringBuilder();
		
		if (mainLogic != null && !mainLogic.isEmpty()) {
			formattedLogic.append("        ").append(mainLogic.replace("\n", "\n        "));
		}
		
		javaCode.append("    private void initializeLogic() {\n");
		javaCode.append(formattedLogic.toString()).append("\n");
		javaCode.append("    }\n\n");
	}
	
	public static void getBlockLogicForLifecycleEvent(StringBuilder javaCode, String activityName, String projectPath) {
		try {
			
			java.util.Set<String> generated = new java.util.HashSet<>();
			
			generated.add("Imports");
			
			String path = projectPath + "/events/lifecycle_events.json";
			File file = new File(path);
			
			if (file.exists()) {
				String json = new String(java.nio.file.Files.readAllBytes(file.toPath()), java.nio.charset.StandardCharsets.UTF_8);
				String decoded = new String(android.util.Base64.decode(json, android.util.Base64.DEFAULT), java.nio.charset.StandardCharsets.UTF_8);
				
				JSONObject allEvents = new JSONObject(decoded);
				JSONArray activityEvents = allEvents.optJSONArray(activityName);
				
				if (activityEvents != null) {
					for (int i = 0; i < activityEvents.length(); i++) {
						String event = activityEvents.optString(i);
						
						if ("Imports".equals(event)) {
							generated.add(event);
							continue;
						}
						
						if (!generated.contains(event)) {
							String eventLogic = generateSystemEventCode(activityName, event);
							if (eventLogic != null && !eventLogic.trim().isEmpty()) {
								addLifecycleEvent(javaCode, activityName, event, projectPath);
							}
							generated.add(event);
						}
					}
				}
			}
			
			String sc_id = extractProjectIdFromPath(projectPath);
			if (sc_id != null) {
				java.util.List<org.json.JSONObject> projectEvents =
				com.nexusteam.blacklogics.utils.EventUtils.loadEventsForActivity(sc_id, activityName);
				
				for (org.json.JSONObject evObj : projectEvents) {
					if (evObj == null) continue;
					
					String eventName    = evObj.optString("name", "");
					String resolvedCode = evObj.optString("code", "");
					String rawTemplate  = evObj.optString("rawTemplate", "");
					
					if ("Imports".equals(eventName)) continue;
					
					if (generated.contains(eventName)) continue;
					if (resolvedCode.isEmpty()) continue;
					if (resolvedCode.trim().equals("%s")) continue;
					
					com.nexusteam.blacklogics.utils.EventUtils.SystemEvent sysEvent =
					com.nexusteam.blacklogics.utils.EventUtils.findSystemEventByName(eventName);
					
					String finalCode;
					if (sysEvent != null && !sysEvent.code.isEmpty()) {
						String currentLogic = generateSystemEventCode(activityName, eventName);
						if (currentLogic == null) currentLogic = "";
						
						if (currentLogic.trim().isEmpty() && sysEvent.code.trim().equals("%s")) {
							continue;
						}
						
						finalCode = sysEvent.buildCode(currentLogic);
					} else if (!rawTemplate.isEmpty()) {
						String currentLogic = generateSystemEventCode(activityName, eventName);
						if (currentLogic == null) currentLogic = "";
						
						if (currentLogic.trim().isEmpty() && rawTemplate.trim().equals("%s")) {
							continue;
						}
						
						finalCode = com.nexusteam.blacklogics.utils.EventUtils
						.resolvePlaceholders(rawTemplate, currentLogic);
					} else {
						finalCode = resolvedCode;
						
						if (finalCode.trim().isEmpty()) {
							continue;
						}
					}
					
					String methodSignature = "public void " + eventName + "(";
					if (finalCode.contains(methodSignature) && finalCode.indexOf(methodSignature) != finalCode.lastIndexOf(methodSignature)) {
						Log.w("JavaCodeBuilder", "Skipping nested method for: " + eventName);
						continue;
					}
					
					javaCode.append(prettyIndent(finalCode, "    ")).append("\n\n");
					generated.add(eventName);
				}
			}
			
			if (isDrawerEnabledFor(activityName) && !generated.contains("onBackPressed")) {
				javaCode.append("    @Override\n");
				javaCode.append("    public void onBackPressed() {\n");
				javaCode.append("        if (_drawer != null && _drawer.isDrawerOpen(androidx.core.view.GravityCompat.START)) {\n");
				javaCode.append("            _drawer.closeDrawer(androidx.core.view.GravityCompat.START);\n");
				javaCode.append("            return;\n");
				javaCode.append("        }\n");
				javaCode.append("        super.onBackPressed();\n");
				javaCode.append("    }\n\n");
				generated.add("onBackPressed");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addCustomFunctions(StringBuilder javaCode, String activityName, String projectPath, String sc_id) {
		try {
			
			File baseLogicFile = new File("/storage/emulated/0/.blacklogics/data/" + sc_id, "baselogic");
			
			if (!baseLogicFile.exists()) {
				return;
			}
			
			String content = new String(
			java.nio.file.Files.readAllBytes(baseLogicFile.toPath()),
			java.nio.charset.StandardCharsets.UTF_8
			);
			
			JSONObject logicJson = new JSONObject(content);
			
			if (!logicJson.has("block_data")) {
				return;
			}
			
			String encoded = logicJson.getString("block_data");
			String decoded = new String(
			android.util.Base64.decode(encoded, android.util.Base64.DEFAULT),
			java.nio.charset.StandardCharsets.UTF_8
			);
			
			JSONObject dataMap = new JSONObject(decoded);
			
			if (dataMap.has("functions")) {
				
				JSONObject functionsMap = dataMap.getJSONObject("functions");
				
				Iterator<String> activityKeys = functionsMap.keys();
				while (activityKeys.hasNext()) {
					String activityKey = activityKeys.next();
					
					if (activityKey.equals(activityName)) {
						
						JSONArray functionPairs = functionsMap.getJSONArray(activityKey);
						
						for (int i = 0; i < functionPairs.length(); i++) {
							JSONObject functionPair = functionPairs.getJSONObject(i);
							
							String functionName = functionPair.getString("first");
							String functionSpec = functionPair.getString("second");
							
							List<HashMap<String, String>> parameters = parseFunctionSpec(functionSpec);
							
							String eventLogic = generateCodeFromBlocks(activityName, functionName + "_moreBlock");
							
							generateFunctionMethod(javaCode, functionName, "void", parameters, eventLogic);
						}
					}
				}
			}
			
			List<HashMap<String, Object>> functions = ProjectLogicRepository.loadFunctions(activityName);
			for (HashMap<String, Object> func : functions) {
				String name = String.valueOf(func.get("functionName"));
				String returnType = String.valueOf(func.get("returnType"));
				
				String eventLogic = generateCodeFromBlocks(activityName, name + "_moreBlock");
				
				@SuppressWarnings("unchecked")
				List<HashMap<String, String>> parameters =
				(List<HashMap<String, String>>) func.get("parameters");
				
				generateFunctionMethod(javaCode, name, returnType, parameters, eventLogic);
			}
			
		} catch (Exception e) {
			Log.e("JavaCodeBuilder", "Error adding custom functions", e);
		}
	}
	
	private static List<HashMap<String, String>> parseFunctionSpec(String spec) {
		List<HashMap<String, String>> parameters = new ArrayList<>();
		
		if (spec == null || spec.isEmpty()) {
			return parameters;
		}
		
		String[] parts = spec.trim().split("\\s+");
		
		for (int i = 1; i < parts.length; i++) {
			String part = parts[i];
			
			if (!part.contains("%")) {
				continue;
			}
			
			HashMap<String, String> param = new HashMap<>();
			
			if (part.startsWith("%b.")) {
				param.put("type", "boolean");
				param.put("name", part.substring(3));
				parameters.add(param);
			} else if (part.startsWith("%d.")) {
				param.put("type", "double");
				param.put("name", part.substring(3));
				parameters.add(param);
			} else if (part.startsWith("%s.")) {
				param.put("type", "String");
				param.put("name", part.substring(3));
				parameters.add(param);
			}
			
		}
		
		return parameters;
	}
	
	private static void generateFunctionMethod(StringBuilder javaCode,
	String functionName,
	String returnType,
	List<HashMap<String, String>> parameters,
	String eventLogic) {
		
		StringBuilder formattedLogic = new StringBuilder();
		if (eventLogic != null && !eventLogic.isEmpty()) {
			formattedLogic.append("        ").append(eventLogic.replace("\n", "\n        "));
		}
		
		javaCode.append("    public ").append(returnType).append(" ").append(functionName).append("(");
		
		List<String> paramStrings = new ArrayList<>();
		if (parameters != null) {
			for (HashMap<String, String> param : parameters) {
				String type = mapParameterType(param.get("type"));
				String name = param.get("name");
				paramStrings.add(type + " " + name);
			}
		}
		
		javaCode.append(String.join(", ", paramStrings)).append(") {\n");
		
		if (formattedLogic.length() > 0) {
			javaCode.append(formattedLogic.toString()).append("\n");
		}
		
		if (!"void".equals(returnType)) {
			javaCode.append("        return ").append(getDefaultReturnValue(returnType)).append(";\n");
		}
		
		javaCode.append("    }\n\n");
	}
	
	private static String mapParameterType(String type) {
		if (type == null) return "Object";
		
		switch (type) {
			case "boolean":
			return "boolean";
			case "double":
			return "double";
			case "String":
			return "String";
			default:
			return "Object";
		}
	}
	
	public static void addUtilityMethods(StringBuilder javaCode, String projectPath) {
		javaCode.append("    public void showMessage(String message) {\n");
		javaCode.append("        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();\n");
		javaCode.append("    }\n\n");
		
		if (new File(projectPath + "/permission").exists()) {
			javaCode.append("    private void initPermissions() {\n");
			javaCode.append("        // Initialize permissions here\n");
			javaCode.append("    }\n\n");
		}
	}
	
	public static void addOnActivityResultMethod(StringBuilder javaCode, String activityName) {
		List<HashMap<String, String>> components = loadComponentsUnified(activityName);
		if (components == null || components.isEmpty()) return;
		
		List<HashMap<String, String>> filePickerComps = new ArrayList<>();
		List<HashMap<String, String>> cameraComps = new ArrayList<>();
		List<HashMap<String, String>> customResultComps = new ArrayList<>();
		
		Map<String, ComponentBean> customByTypeName = buildCustomComponentMap();
		
		for (HashMap<String, String> comp : components) {
			String type = comp.get("componentName");
			if ("FilePicker".equals(type)) { filePickerComps.add(comp); continue; }
			if ("Camera".equals(type) || "Camera2 API".equals(type)) { cameraComps.add(comp); continue; }
			if (isCustomResultBased(type, customByTypeName)) { customResultComps.add(comp); }
		}
		
		if (filePickerComps.isEmpty() && cameraComps.isEmpty() && customResultComps.isEmpty()) return;
		
		javaCode.append("    @Override\n");
		javaCode.append("    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {\n");
		javaCode.append("        super.onActivityResult(_requestCode, _resultCode, _data);\n\n");
		javaCode.append("        switch (_requestCode) {\n");
		
		for (int i = 0; i < filePickerComps.size(); i++) {
			HashMap<String, String> comp = filePickerComps.get(i);
			String name = comp.get("fieldName");
			if (name == null || name.isEmpty()) continue;
			
			String onSuccessLogic = generateCodeFromBlocks(activityName, name + "_onFilesPicked");
			if (onSuccessLogic == null || onSuccessLogic.isEmpty()) {
				onSuccessLogic = generateCodeFromBlocks(activityName, name + "_onFilePicked");
			}
			String onCancelLogic = generateCodeFromBlocks(activityName, name + "_onFilesPickedCancel");
			
			javaCode.append("            case REQ_CD_").append(name.toUpperCase()).append(":\n");
			javaCode.append("            if (_resultCode == Activity.RESULT_OK) {\n");
			javaCode.append("                ArrayList<String> _filePath = new ArrayList<>();\n");
			javaCode.append("                if (_data != null) {\n");
			javaCode.append("                    if (_data.getClipData() != null) {\n");
			javaCode.append("                        for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {\n");
			javaCode.append("                            ClipData.Item _item = _data.getClipData().getItemAt(_index);\n");
			javaCode.append("                            _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));\n");
			javaCode.append("                        }\n");
			javaCode.append("                    } else {\n");
			javaCode.append("                        _filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));\n");
			javaCode.append("                    }\n");
			javaCode.append("                }\n");
			if (onSuccessLogic != null && !onSuccessLogic.isEmpty()) {
				javaCode.append("                ").append(onSuccessLogic.replace("\n", "\n                ")).append("\n");
			}
			javaCode.append("            } else {\n");
			if (onCancelLogic != null && !onCancelLogic.isEmpty()) {
				javaCode.append("                ").append(onCancelLogic.replace("\n", "\n                ")).append("\n");
			}
			javaCode.append("            }\n");
			javaCode.append("            break;\n\n");
		}
		
		for (HashMap<String, String> comp : cameraComps) {
			String type = comp.get("componentName");
			String name = comp.get("fieldName");
			if (name == null || name.isEmpty()) continue;
			if ("Camera2 API".equals(type)) continue;
			
			String onSuccessLogic = generateCodeFromBlocks(activityName, name + "_onPictureTaken");
			String onCancelLogic = generateCodeFromBlocks(activityName, name + "_onPictureTakenCancel");
			
			javaCode.append("            case REQ_CD_").append(name.toUpperCase()).append(":\n");
			javaCode.append("            if (_resultCode == Activity.RESULT_OK) {\n");
			javaCode.append("                String _filePath = _file_").append(name).append(".getAbsolutePath();\n");
			if (onSuccessLogic != null && !onSuccessLogic.isEmpty()) {
				javaCode.append("                ").append(onSuccessLogic.replace("\n", "\n                ")).append("\n");
			}
			javaCode.append("            } else {\n");
			if (onCancelLogic != null && !onCancelLogic.isEmpty()) {
				javaCode.append("                ").append(onCancelLogic.replace("\n", "\n                ")).append("\n");
			}
			javaCode.append("            }\n");
			javaCode.append("            break;\n\n");
		}
		
		for (HashMap<String, String> comp : customResultComps) {
			String name = comp.get("fieldName");
			ComponentBean cb = customByTypeName.get(comp.get("componentName"));
			
			javaCode.append("            case ").append(name.toUpperCase()).append("_REQUEST:\n");
			javaCode.append("            if (_resultCode == Activity.RESULT_OK) {\n");
			appendBlockLogic(javaCode, activityName, name + "_" + cb.events.get(0), "                ");
			javaCode.append("            } else {\n");
			if (cb.events.size() > 1) {
				appendBlockLogic(javaCode, activityName, name + "_" + cb.events.get(1), "                ");
			}
			javaCode.append("            }\n");
			javaCode.append("            break;\n\n");
		}
		
		if (!filePickerComps.isEmpty()) {
			javaCode.append("            case 1001:\n");
			javaCode.append("            initializeLogic();\n");
			javaCode.append("            break;\n\n");
		}
		javaCode.append("            default:\n");
		javaCode.append("            break;\n");
		javaCode.append("        }\n");
		
		String _userOarLogic = generateSystemEventCode(activityName, "onActivityResult");
		if (_userOarLogic == null || _userOarLogic.trim().isEmpty()) {
			com.nexusteam.blacklogics.utils.EventUtils.SystemEvent _sysEv =
			com.nexusteam.blacklogics.utils.EventUtils.findSystemEventByName("onActivityResult");
			if (_sysEv != null && _sysEv.code != null && !_sysEv.code.isEmpty()
			&& !_sysEv.code.equals("%s")) {
				_userOarLogic = _sysEv.buildCode("");
			}
		}
		if (_userOarLogic != null && !_userOarLogic.trim().isEmpty()) {
			javaCode.append("\n");
			javaCode.append("        ").append(_userOarLogic.replace("\n", "\n        ")).append("\n");
		}
		
		javaCode.append("    }\n\n");
		
		if (!filePickerComps.isEmpty() || !cameraComps.isEmpty()) {
			javaCode.append("    private void _afterPermissions() {\n");
			if (!filePickerComps.isEmpty()) {
				javaCode.append("        if (android.os.Build.VERSION.SDK_INT >= 30 && !android.os.Environment.isExternalStorageManager()) {\n");
				javaCode.append("            try {\n");
				javaCode.append("                Intent _i = new Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse(\"package:\" + getPackageName()));\n");
				javaCode.append("                startActivityForResult(_i, 1001);\n");
				javaCode.append("            } catch (Exception _e) {\n");
				javaCode.append("                try {\n");
				javaCode.append("                    startActivityForResult(new Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), 1001);\n");
				javaCode.append("                } catch (Exception _e2) {\n");
				javaCode.append("                    initializeLogic();\n");
				javaCode.append("                }\n");
				javaCode.append("            }\n");
				javaCode.append("        } else {\n");
				javaCode.append("            initializeLogic();\n");
				javaCode.append("        }\n");
			} else {
				javaCode.append("        initializeLogic();\n");
			}
			javaCode.append("    }\n\n");
			
			javaCode.append("    @Override\n");
			javaCode.append("    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {\n");
			javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
			javaCode.append("        if (requestCode == 1000) {\n");
			javaCode.append("            boolean _allGranted = true;\n");
			javaCode.append("            for (int _r : grantResults) {\n");
			javaCode.append("                if (_r != android.content.pm.PackageManager.PERMISSION_GRANTED) { _allGranted = false; break; }\n");
			javaCode.append("            }\n");
			javaCode.append("            if (_allGranted) {\n");
			javaCode.append("                _afterPermissions();\n");
			javaCode.append("            } else {\n");
			javaCode.append("                new android.app.AlertDialog.Builder(this)\n");
			javaCode.append("                    .setTitle(\"Permission required\")\n");
			javaCode.append("                    .setMessage(\"Camera/Storage permission is needed for this feature. Open Settings and allow it.\")\n");
			javaCode.append("                    .setCancelable(false)\n");
			javaCode.append("                    .setPositiveButton(\"Settings\", new android.content.DialogInterface.OnClickListener() {\n");
			javaCode.append("                        @Override\n");
			javaCode.append("                        public void onClick(android.content.DialogInterface d, int w) {\n");
			javaCode.append("                            android.content.Intent _i = new android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);\n");
			javaCode.append("                            _i.setData(android.net.Uri.fromParts(\"package\", getPackageName(), null));\n");
			javaCode.append("                            startActivity(_i);\n");
			javaCode.append("                            initializeLogic();\n");
			javaCode.append("                        }\n");
			javaCode.append("                    })\n");
			javaCode.append("                    .setNegativeButton(\"Cancel\", new android.content.DialogInterface.OnClickListener() {\n");
			javaCode.append("                        @Override\n");
			javaCode.append("                        public void onClick(android.content.DialogInterface d, int w) {\n");
			javaCode.append("                            initializeLogic();\n");
			javaCode.append("                        }\n");
			javaCode.append("                    })\n");
			javaCode.append("                    .show();\n");
			javaCode.append("            }\n");
			javaCode.append("        }\n");
			javaCode.append("    }\n");
			javaCode.append("\n");
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Map<String, String>> generateWidgetEventLogicsFromBlocks(String activityName) {
		Map<String, Map<String, String>> result = new LinkedHashMap<String, Map<String, String>>();
		if (activityName == null || activityName.isEmpty()) return result;
		
		Object[] all;
		try {
			all = BlockStorage.readAll();
		} catch (Exception e) {
			Log.e("JavaCodeBuilder", "BlockStorage.readAll failed", e);
			return result;
		}
		Map<String, ArrayList<BlockBean>> blocksMap = (Map<String, ArrayList<BlockBean>>) all[0];
		if (blocksMap == null) return result;
		
		String suffix = "_" + activityName;
		
		for (Map.Entry<String, ArrayList<BlockBean>> entry : blocksMap.entrySet()) {
			String fullKey = entry.getKey();
			if (fullKey == null || !fullKey.endsWith(suffix)) continue;
			
			String remainder = fullKey.substring(0, fullKey.length() - suffix.length());
			
			int firstSpace = remainder.indexOf(' ');
			String widgetAndListener = (firstSpace == -1) ? remainder : remainder.substring(0, firstSpace);
			
			int lastUnderscore = widgetAndListener.lastIndexOf("_");
			if (lastUnderscore == -1 || lastUnderscore >= widgetAndListener.length() - 1) continue;
			
			String widgetId = widgetAndListener.substring(0, lastUnderscore);
			String eventType = widgetAndListener.substring(lastUnderscore + 1);
			
			ArrayList<BlockBean> blocks = entry.getValue();
			if (blocks == null || blocks.isEmpty()) continue;
			
			String code;
			try {
				code = new hb(eventType, new es(), activityName, blocks).a();
			} catch (Exception e) {
				Log.e("JavaCodeBuilder", "hb generation failed for: " + fullKey, e);
				continue;
			}
			if (code == null || code.trim().isEmpty()) continue;
			
			if (!result.containsKey(widgetId)) {
				result.put(widgetId, new LinkedHashMap<String, String>());
			}
			result.get(widgetId).put(eventType, code);
		}
		
		return result;
	}
	
	private static void addWidgetEventBindings(StringBuilder javaCode, String activityName, String layoutPath) {
		Map<String, Map<String, String>> widgetEventsMap = generateWidgetEventLogicsFromBlocks(activityName);
		Map<String, String> widgets = extractWidgetsFromLayout(activityName, layoutPath);
		
		Object[] cvInfo = extractWidgetsAndCustomViews(activityName, layoutPath);
		@SuppressWarnings("unchecked")
		Map<String, String> widgetCustomViews = (Map<String, String>) cvInfo[1];
		
		for (String widgetId : widgets.keySet()) {
			Map<String, String> events = widgetEventsMap.get(widgetId);
			if (events == null) continue;
			
			String widgetClassName = widgets.get(widgetId);
			boolean hasCustomView = widgetCustomViews.containsKey(widgetId);
			
			String clickLogic = events.get("onClick");
			if (clickLogic != null && !clickLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onClick").bind(javaCode, widgetId, clickLogic);
			}
			
			String longClickLogic = events.get("onLongClick");
			if (longClickLogic != null && !longClickLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onLongClick").bind(javaCode, widgetId, longClickLogic);
			}
			
			String touchLogic = events.get("onTouch");
			if (touchLogic != null && !touchLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onTouch").bind(javaCode, widgetId, touchLogic);
			}
			
			String textChangeLogic = events.get("onTextChanged");
			if (textChangeLogic != null && !textChangeLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onTextChanged").bind(javaCode, widgetId, textChangeLogic);
			}
			
			String checkedChangeLogic = events.get("onCheckedChanged");
			if (checkedChangeLogic != null && !checkedChangeLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onCheckedChanged").bind(javaCode, widgetId, checkedChangeLogic);
			}
			
			if (!hasCustomView) {
				String itemSelectedLogic = events.get("onItemSelected");
				if (itemSelectedLogic != null && !itemSelectedLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onItemSelected").bind(javaCode, widgetId, itemSelectedLogic);
				}
			}
			
			String progressLogic = events.get("onProgressChanged");
			String startTrackLogic = events.get("onStartTrackingTouch");
			String stopTrackLogic = events.get("onStopTrackingTouch");
			boolean hasProgress = progressLogic != null && !progressLogic.trim().isEmpty();
			boolean hasStartTrack = startTrackLogic != null && !startTrackLogic.trim().isEmpty();
			boolean hasStopTrack = stopTrackLogic != null && !stopTrackLogic.trim().isEmpty();
			if (hasProgress || hasStartTrack || hasStopTrack) {
				WidgetEventResolver.bindSeekBarFull(javaCode, widgetId, progressLogic, startTrackLogic, stopTrackLogic);
			}
			
			String focusLogic = events.get("onFocusChange");
			if (focusLogic != null && !focusLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onFocusChange").bind(javaCode, widgetId, focusLogic);
			}
			
			String ratingLogic = events.get("onRatingChanged");
			if (ratingLogic != null && !ratingLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onRatingChanged").bind(javaCode, widgetId, ratingLogic);
			}
			
			String scrollLogic = events.get("onScrollChanged");
			if (scrollLogic != null && !scrollLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onScrollChanged").bind(javaCode, widgetId, scrollLogic);
			}
			
			String pageStartedLogic = events.get("onPageStarted");
			String pageFinishedLogic = events.get("onPageFinished");
			boolean hasPageStarted = pageStartedLogic != null && !pageStartedLogic.trim().isEmpty();
			boolean hasPageFinished = pageFinishedLogic != null && !pageFinishedLogic.trim().isEmpty();
			if (hasPageStarted || hasPageFinished) {
				WidgetEventResolver.bindWebViewClientFull(javaCode, widgetId, pageStartedLogic, pageFinishedLogic);
			}
			
			String pageSelectedLogic = events.get("onPageSelected");
			String pageScrolledLogic = events.get("onPageScrolled");
			String pageScrollStateChangedLogic = events.get("onPageScrollStateChanged");
			boolean hasPageSelected = pageSelectedLogic != null && !pageSelectedLogic.trim().isEmpty();
			boolean hasPageScrolled = pageScrolledLogic != null && !pageScrolledLogic.trim().isEmpty();
			boolean hasPageScrollStateChanged = pageScrollStateChangedLogic != null && !pageScrollStateChangedLogic.trim().isEmpty();
			boolean hasAnyPageEvent = hasPageSelected || hasPageScrolled || hasPageScrollStateChanged;
			String widgetClassNameLower = widgetClassName != null
			? widgetClassName.toLowerCase(java.util.Locale.ENGLISH) : "";
			if (hasAnyPageEvent && widgetClassNameLower.contains("viewpager2")) {
				WidgetEventResolver.bindViewPager2Full(javaCode, widgetId, pageSelectedLogic, pageScrolledLogic, pageScrollStateChangedLogic);
			} else if (hasAnyPageEvent && widgetClassNameLower.contains("viewpager")) {
				WidgetEventResolver.bindViewPagerFull(javaCode, widgetId, pageSelectedLogic, pageScrolledLogic, pageScrollStateChangedLogic);
			}
			
			String tabSelectedLogic = events.get("onTabSelected");
			String tabUnselectedLogic = events.get("onTabUnselected");
			String tabReselectedLogic = events.get("onTabReselected");
			boolean hasTabEvent = (tabSelectedLogic != null && !tabSelectedLogic.trim().isEmpty())
			|| (tabUnselectedLogic != null && !tabUnselectedLogic.trim().isEmpty())
			|| (tabReselectedLogic != null && !tabReselectedLogic.trim().isEmpty());
			if (hasTabEvent) {
				WidgetEventResolver.bindTabLayoutFull(javaCode, widgetId, tabSelectedLogic, tabUnselectedLogic, tabReselectedLogic);
			}
			
			String chipCheckedLogic = events.get("onChipCheckedChanged");
			if (chipCheckedLogic != null && !chipCheckedLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onChipCheckedChanged").bind(javaCode, widgetId, chipCheckedLogic);
			}
			
			String navItemLogic = events.get("onNavigationItemSelected");
			if (navItemLogic != null && !navItemLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onNavigationItemSelected").bind(javaCode, widgetId, navItemLogic);
			}
			String bottomNavItemLogic = events.get("onNavItemSelected");
			if (bottomNavItemLogic != null && !bottomNavItemLogic.trim().isEmpty()) {
				WidgetEventResolver.resolve(widgetClassName, "onNavItemSelected").bind(javaCode, widgetId, bottomNavItemLogic);
			}
			
			boolean isAdapterView = WidgetEventResolver.isAdapterViewWidget(widgetClassName);
			if (isAdapterView && !hasCustomView) {
				String itemClickLogic = events.get("onItemClick");
				if (itemClickLogic != null && !itemClickLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onItemClick").bind(javaCode, widgetId, itemClickLogic);
				}
			}
			if (isAdapterView) {
				
				String itemLongClickLogic = events.get("onItemLongClick");
				if (itemLongClickLogic != null && !itemLongClickLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onItemLongClick").bind(javaCode, widgetId, itemLongClickLogic);
				}
			}
			
			if (WidgetEventResolver.isExpandableListWidget(widgetClassName)) {
				if (!hasCustomView) {
					String childClickLogic = events.get("onChildClick");
					if (childClickLogic != null && !childClickLogic.trim().isEmpty()) {
						WidgetEventResolver.resolve(widgetClassName, "onChildClick").bind(javaCode, widgetId, childClickLogic);
					}
				}
				String childLongClickLogic = events.get("onChildLongClick");
				if (childLongClickLogic != null && !childLongClickLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onChildLongClick").bind(javaCode, widgetId, childLongClickLogic);
				}
				String groupClickLogic = events.get("onGroupClick");
				if (groupClickLogic != null && !groupClickLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onGroupClick").bind(javaCode, widgetId, groupClickLogic);
				}
				String groupExpandLogic = events.get("onGroupExpand");
				if (groupExpandLogic != null && !groupExpandLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onGroupExpand").bind(javaCode, widgetId, groupExpandLogic);
				}
				String groupCollapseLogic = events.get("onGroupCollapse");
				if (groupCollapseLogic != null && !groupCollapseLogic.trim().isEmpty()) {
					WidgetEventResolver.resolve(widgetClassName, "onGroupCollapse").bind(javaCode, widgetId, groupCollapseLogic);
				}
			}
		}
	}
	private static Map<String, String> extractWidgetsFromLayout(
	String activityName,
	String layoutPath
	) {
		return LayoutLoader.extractWidgetsFromLayout(activityName, layoutPath);
	}
	
	private static String readEncryptedJson(File file) throws Exception {
		
		long modified = file.lastModified();
		
		if (cachedLayoutJson != null && cacheLastModified == modified) {
			return cachedLayoutJson;
		}
		
		byte[] decrypted = FileEncryptionUtil.decryptFromFile(file, ENC_PASSWORD);
		
		String json = new String(decrypted, "UTF-8");
		
		cachedLayoutJson = json;
		cacheLastModified = modified;
		
		return json;
	}
	
	private static ArrayList<LayoutData> readEncryptedBinaryLayouts(File file)
	throws Exception {
		
		byte[] decrypted =
		FileEncryptionUtil.decryptFromFile(file, ENC_PASSWORD);
		
		ObjectInputStream ois =
		new ObjectInputStream(
		new ByteArrayInputStream(decrypted)
		);
		
		Object obj = ois.readObject();
		ois.close();
		
		if (!(obj instanceof ArrayList)) {
			return new ArrayList<>();
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<LayoutData> list =
		(ArrayList<LayoutData>) obj;
		
		return list;
	}
	
	private static void parseWidgetsFromXML(String xml,
	Map<String, String> widgets,
	Map<String, String> widgetCustomViews) {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			
			int type;
			while ((type = parser.next()) != XmlPullParser.END_DOCUMENT) {
				if (type == XmlPullParser.START_TAG) {
					String tag        = parser.getName();
					String widgetType = mapTagToWidgetType(tag);
					
					String foundId         = null;
					String foundCustomView = null;
					
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						String attrName  = parser.getAttributeName(i);
						String attrValue = parser.getAttributeValue(i);
						if ("android:id".equals(attrName)) {
							foundId = AttributeUtils.getName(attrValue);
						} else if ("app:customView".equals(attrName)) {
							foundCustomView = attrValue.trim();
						}
					}
					
					if (foundId != null && !foundId.isEmpty()) {
						widgets.put(foundId, widgetType);
						if (foundCustomView != null && !foundCustomView.isEmpty()
						&& !"none".equalsIgnoreCase(foundCustomView)) {
							widgetCustomViews.put(foundId, foundCustomView);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e("WidgetExtract", "parseWidgetsFromXML failed", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Object[] extractWidgetsAndCustomViews(String activityName, String layoutPath) {
		Map<String, String> widgets           = new LinkedHashMap<String, String>();
		Map<String, String> widgetCustomViews = new LinkedHashMap<String, String>();
		try {
			String xml = LayoutLoader.getLayoutXml(activityName, layoutPath);
			if (xml != null && !xml.isEmpty()) {
				parseWidgetsFromXML(xml, widgets, widgetCustomViews);
			}
		} catch (Exception e) {
			Log.e("JavaCodeBuilder", "extractWidgetsAndCustomViews failed", e);
		}
		return new Object[]{ widgets, widgetCustomViews };
	}
	
	private String decodeUnicodeEscapes(String input) {
		StringBuilder output = new StringBuilder();
		int i = 0;
		while (i < input.length()) {
			if (input.charAt(i) == '\\' && i + 5 < input.length() && input.charAt(i + 1) == 'u') {
				try {
					String hex = input.substring(i + 2, i + 6);
					char unicodeChar = (char) Integer.parseInt(hex, 16);
					output.append(unicodeChar);
					i += 6;
				} catch (NumberFormatException e) {
					output.append(input.charAt(i));
					i++;
				}
			} else {
				output.append(input.charAt(i));
				i++;
			}
		}
		return output.toString();
	}
	
	private String extractTagName(String xmlLine) {
		int tagStart = xmlLine.indexOf("<") + 1;
		if (tagStart == 0) return "View";
		
		if (xmlLine.charAt(tagStart) == '?') return "View";
		
		int tagEnd = xmlLine.length();
		for (int i = tagStart; i < xmlLine.length(); i++) {
			char c = xmlLine.charAt(i);
			if (c == ' ' || c == '>' || c == '/' || c == '\n' || c == '\t') {
				tagEnd = i;
				break;
			}
		}
		return (tagEnd > tagStart) ? xmlLine.substring(tagStart, tagEnd) : "View";
	}
	
	private static String getInitialValue(String type) {
		if (type == null) return "null";
		switch (type) {
			case "int": return "0";
			case "boolean": return "false";
			case "String": return "\"\"";
			case "double": return "0.0";
			case "float": return "0.0f";
			case "long": return "0L";
			case "ArrayList<String>":
			case "ArrayList<Double>":
			case "ArrayList<HashMap<String, Object>>":
			case "List<Double>":
			case "List<String>":
			case "List<Map<String, Object>>":
			return "new ArrayList<>()";
			case "HashMap<String, Object>":
			return "new HashMap<>()";
			default: return "null";
		}
	}
	
	private static String getDefaultReturnValue(String returnType) {
		if (returnType == null) return "null";
		switch (returnType) {
			case "int": return "0";
			case "boolean": return "false";
			case "String": return "\"\"";
			case "double": return "0.0";
			case "float": return "0.0f";
			case "long": return "0L";
			case "ArrayList<String>":
			case "ArrayList<Double>":
			case "ArrayList<HashMap<String, Object>>":
			return "new ArrayList<>()";
			case "HashMap<String, Object>":
			return "new HashMap<>()";
			default: return "null";
		}
	}
	
	public static void addMoreBlocksCode(StringBuilder javaCode, String activityName, String sc_id) {
		try {
			BlockStorage.setScId(sc_id);
			
			ArrayList<Pair<String, String>> functions = BlockStorage.loadFunctions(activityName + ".java");
			
			if (functions == null || functions.isEmpty()) {
				return;
			}
			
			for (Pair<String, String> function : functions) {
				String functionName = function.first;
				String functionSpec = function.second;
				
				String eventLogic = generateCodeFromBlocks(activityName, functionName + "_moreBlock");
				
				String methodName = functionName.startsWith("_") ? functionName : "_" + functionName;
				javaCode.append("\n    public void ").append(methodName).append("(");
				
				String[] parts = functionSpec.split("\\s+");
				StringBuilder params = new StringBuilder();
				List<String> paramTypes = new ArrayList<>();
				
				for (int i = 1; i < parts.length; i++) {
					String part = parts[i];
					if (part.startsWith("%")) {
						if (params.length() > 0) {
							params.append(", ");
						}
						
						char category = part.charAt(1);
						String type;
						String paramName;
						
						int firstDot = part.indexOf('.');
						int lastDot = part.lastIndexOf('.');
						
						if (firstDot > 0 && lastDot > firstDot) {
							
							String subtype = part.substring(firstDot + 1, lastDot);
							paramName = (lastDot < part.length() - 1)
							? part.substring(lastDot + 1) : "param" + i;
							type = mapSubtypeToJavaType(subtype);
						} else {
							
							switch (category) {
								case 'n':
								case 'd':
								type = "double";
								break;
								case 'b':
								type = "boolean";
								break;
								case 'c':
								type = "int";
								break;
								case 's':
								case 't':
								default:
								type = "String";
								break;
							}
							
							if (firstDot > 0 && firstDot < part.length() - 1) {
								paramName = part.substring(firstDot + 1);
							} else {
								paramName = "param" + i;
							}
						}
						
						params.append(type).append(" _").append(paramName);
						paramTypes.add(type);
					}
				}
				
				javaCode.append(params.toString()).append(") {\n");
				
				if (eventLogic != null && !eventLogic.isEmpty()) {
					String processedLogic = eventLogic;
					for (int i = 0; i < paramTypes.size(); i++) {
						processedLogic = processedLogic.replace("param" + (i+1),
						getParamNameFromSpec(functionSpec, i+1));
					}
					javaCode.append("        ").append(processedLogic.replace("\n", "\n        ")).append("\n");
				}
				
				javaCode.append("    }\n\n");
			}
			
		} catch (Exception e) {
			Log.e("JavaCodeBuilder", "Error adding more blocks", e);
		}
	}
	
	private static String getParamNameFromSpec(String spec, int paramIndex) {
		String[] parts = spec.split("\\s+");
		int currentParam = 0;
		
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].startsWith("%")) {
				currentParam++;
				if (currentParam == paramIndex) {
					String part = parts[i];
					
					int firstDot = part.indexOf('.');
					int lastDot = part.lastIndexOf('.');
					int splitDot = (firstDot > 0 && lastDot > firstDot) ? lastDot : firstDot;
					if (splitDot > 0 && splitDot < part.length() - 1) {
						return "_" + part.substring(splitDot + 1);
					}
					return "_param" + paramIndex;
				}
			}
		}
		return "_param" + paramIndex;
	}
	
	private static String mapSubtypeToJavaType(String subtype) {
		if (subtype == null) return "Object";
		switch (subtype) {
			
			case "view": return "View";
			case "textview": return "TextView";
			case "edittext": return "EditText";
			case "imageview": return "ImageView";
			case "checkbox": return "CheckBox";
			case "switch": return "Switch";
			case "listview": return "ListView";
			case "webview": return "WebView";
			case "progressbar": return "ProgressBar";
			case "calendarview": return "CalendarView";
			case "adview": return "AdView";
			case "mapview": return "MapView";
			case "spinner": return "Spinner";
			case "seekbar": return "SeekBar";
			
			case "intent": return "Intent";
			case "file": return "File";
			case "calendar": return "Calendar";
			case "timer": return "Timer";
			case "vibrator": return "Vibrator";
			case "mediaplayer": return "MediaPlayer";
			case "dialog": return "Dialog";
			case "soundpool": return "SoundPool";
			case "objectanimator": return "ObjectAnimator";
			case "firebase": return "DatabaseReference";
			case "firebaseauth": return "FirebaseAuth";
			case "firebasestorage": return "StorageReference";
			case "gyroscope": return "Sensor";
			case "requestnetwork": return "RequestNetwork";
			case "texttospeech": return "TextToSpeech";
			case "speechtotext": return "SpeechToText";
			case "bluetoothconnect": return "BluetoothConnect";
			case "locationmanager": return "LocationManager";
			
			case "listInt": return "ArrayList<Integer>";
			case "listStr": return "ArrayList<String>";
			case "listMap": return "ArrayList<HashMap<String,Object>>";
			
			case "varMap": return "HashMap<String,Object>";
			
			default: return "Object";
		}
	}
	
	private static String extractProjectIdFromPath(String projectPath) {
		if (projectPath == null || projectPath.isEmpty()) {
			return null;
		}
		
		String[] parts = projectPath.split("/");
		for (int i = 0; i < parts.length; i++) {
			if ("projects".equals(parts[i]) && i + 1 < parts.length) {
				return parts[i + 1];
			}
		}
		
		return null;
	}
	
	private static boolean hasPermissions(String activityName) {
		return false;
	}
	
	private static String mapTagToWidgetType(String tag) {
		if (tag == null) return "View";
		if (tag.contains(".")) {
			return tag.substring(tag.lastIndexOf('.') + 1);
		}
		return tag;
	}
	
	private static void addLifecycleEvent(StringBuilder javaCode, String activityName, String event, String projectPath) {
		
		if ("Imports".equals(event)) {
			return;
		}
		// Fragment lifecycle signatures are emitted by FragmentCodeBuilder – skip here.
		{
			ActivityTypeRegistry _reg = ActivityTypeRegistry.getInstance();
			com.nexusteam.blacklogics.generator.source.model.ActivityConfig _cfg = _reg.getActivityConfig(activityName);
			if (_cfg != null && _cfg.getType() != null && _cfg.getType().isFragment()) {
				return;
			}
		}
		
		String eventLogic = generateSystemEventCode(activityName, event);
		
		if (eventLogic == null || eventLogic.isEmpty()) {
			com.nexusteam.blacklogics.utils.EventUtils.SystemEvent sysEvent =
			com.nexusteam.blacklogics.utils.EventUtils.findSystemEventByName(event);
			if (sysEvent != null && !sysEvent.code.isEmpty()
			&& !sysEvent.code.equals("%s")) {
				eventLogic = sysEvent.buildCode("");
			}
		}
		
		if (eventLogic == null || eventLogic.trim().isEmpty()) {
			return;
		}
		
		StringBuilder formattedLogic = new StringBuilder();
		if (eventLogic != null && !eventLogic.isEmpty()) {
			formattedLogic.append("        ").append(eventLogic.replace("\n", "\n        "));
		}
		
		switch (event) {
			case "onCreate":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onCreate(Bundle savedInstanceState) {\n");
			javaCode.append("        super.onCreate(savedInstanceState);\n");
			javaCode.append("        setContentView(R.layout.activity_").append(activityName.toLowerCase()).append(");\n");
			javaCode.append("        initViews();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onStart":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onStart() {\n");
			javaCode.append("        super.onStart();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onResume":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onResume() {\n");
			javaCode.append("        super.onResume();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onPause":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onPause() {\n");
			javaCode.append("        super.onPause();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onStop":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onStop() {\n");
			javaCode.append("        super.onStop();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onRestart":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onRestart() {\n");
			javaCode.append("        super.onRestart();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onDestroy":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onDestroy() {\n");
			javaCode.append("        super.onDestroy();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onSaveInstanceState":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onSaveInstanceState(Bundle outState) {\n");
			javaCode.append("        super.onSaveInstanceState(outState);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onRestoreInstanceState":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onRestoreInstanceState(Bundle savedInstanceState) {\n");
			javaCode.append("        super.onRestoreInstanceState(savedInstanceState);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onActivityResult":
			{
				List<HashMap<String, String>> _comps = loadComponentsUnified(activityName);
				boolean _hasPickerOrCamera = false;
				Map<String, ComponentBean> _customMap = buildCustomComponentMap();
				if (_comps != null) {
					for (HashMap<String, String> _c : _comps) {
						String _t = _c.get("componentName");
						if ("FilePicker".equals(_t) || "Camera".equals(_t) || "Camera2 API".equals(_t)) {
							_hasPickerOrCamera = true;
							break;
						}
						if (isCustomResultBased(_t, _customMap)) {
							_hasPickerOrCamera = true;
							break;
						}
					}
				}
				if (!_hasPickerOrCamera) {
					javaCode.append("    @Override\n");
					javaCode.append("    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {\n");
					javaCode.append("        super.onActivityResult(_requestCode, _resultCode, _data);\n");
					javaCode.append(formattedLogic.toString()).append("\n");
					javaCode.append("    }\n\n");
				}
			}
			break;
			
			case "onCreateOptionsMenu":
			javaCode.append("    @Override\n");
			javaCode.append("    public boolean onCreateOptionsMenu(Menu menu) {\n");
			javaCode.append("        super.onCreateOptionsMenu(menu);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("        return true;\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onOptionsItemSelected":
			javaCode.append("    @Override\n");
			javaCode.append("    public boolean onOptionsItemSelected(MenuItem item) {\n");
			javaCode.append("        super.onOptionsItemSelected(item);\n");
			javaCode.append("        int id = item.getItemId();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("        return true;\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onBackPressed":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onBackPressed() {\n");
			if (isDrawerEnabledFor(activityName)) {
				javaCode.append("        if (_drawer != null && _drawer.isDrawerOpen(androidx.core.view.GravityCompat.START)) {\n");
				javaCode.append("            _drawer.closeDrawer(androidx.core.view.GravityCompat.START);\n");
				javaCode.append("            return;\n");
				javaCode.append("        }\n");
			}
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("        super.onBackPressed();\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onRequestPermissionsResult":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {\n");
			javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onConfigurationChanged":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onConfigurationChanged(Configuration newConfig) {\n");
			javaCode.append("        super.onConfigurationChanged(newConfig);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onUserInteraction":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onUserInteraction() {\n");
			javaCode.append("        super.onUserInteraction();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onWindowFocusChanged":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onWindowFocusChanged(boolean hasFocus) {\n");
			javaCode.append("        super.onWindowFocusChanged(hasFocus);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onTrimMemory":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onTrimMemory(int level) {\n");
			javaCode.append("        super.onTrimMemory(level);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onLowMemory":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onLowMemory() {\n");
			javaCode.append("        super.onLowMemory();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onPostCreate":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onPostCreate(Bundle savedInstanceState) {\n");
			javaCode.append("        super.onPostCreate(savedInstanceState);\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onPostResume":
			javaCode.append("    @Override\n");
			javaCode.append("    protected void onPostResume() {\n");
			javaCode.append("        super.onPostResume();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			case "onContentChanged":
			javaCode.append("    @Override\n");
			javaCode.append("    public void onContentChanged() {\n");
			javaCode.append("        super.onContentChanged();\n");
			javaCode.append(formattedLogic.toString()).append("\n");
			javaCode.append("    }\n\n");
			break;
			
			default:
			{
				com.nexusteam.blacklogics.utils.EventUtils.SystemEvent sysEvent =
				com.nexusteam.blacklogics.utils.EventUtils.findSystemEventByName(event);
				
				if (sysEvent != null && !sysEvent.code.isEmpty()) {
					String userLogic = (eventLogic != null) ? eventLogic : "";
					String resolvedCode = sysEvent.buildCode(userLogic);
					
					javaCode.append("    ")
					.append(resolvedCode.replace("\n", "\n    "))
					.append("\n\n");
				} else {
					javaCode.append("    // Event: ").append(event).append("\n");
					javaCode.append("    private void on_").append(event).append("() {\n");
					if (formattedLogic.length() > 0) {
						javaCode.append(formattedLogic.toString()).append("\n");
					}
					javaCode.append("    }\n\n");
				}
			}
			break;
		}
	}
	
	public static Map<String, String> extractWidgetsFromItemLayout(String layoutPath, String itemLayoutName) {
		return extractWidgetsFromLayout(itemLayoutName, layoutPath);
	}
	
	public static void addRecyclerViewAdapter(StringBuilder javaCode,
	String recyclerViewId,
	String itemLayoutName,
	String layoutPath) {
		
		Map<String, String> itemWidgets = extractWidgetsFromItemLayout(layoutPath, itemLayoutName);
		
		String adapterClassName = recyclerViewId.substring(0, 1).toUpperCase() +
		recyclerViewId.substring(1) + "Adapter";
		String itemClassName = recyclerViewId.substring(0, 1).toUpperCase() +
		recyclerViewId.substring(1) + "Item";
		
		javaCode.append("\n    /*\n");
		javaCode.append("     * AUTO GENERATED BY BlackLogics\n");
		javaCode.append("     * Adapter for ").append(recyclerViewId).append("\n");
		javaCode.append("     */\n");
		
		javaCode.append("    private class ").append(adapterClassName).append(" extends RecyclerView.Adapter<").append(adapterClassName).append(".ViewHolder> {\n\n");
		
		javaCode.append("        private List<").append(itemClassName).append("> itemList;\n");
		javaCode.append("        private Context context;\n\n");
		
		javaCode.append("        public ").append(adapterClassName).append("(Context context, List<").append(itemClassName).append("> itemList) {\n");
		javaCode.append("            this.context = context;\n");
		javaCode.append("            this.itemList = itemList;\n");
		javaCode.append("        }\n\n");
		
		javaCode.append("        @Override\n");
		javaCode.append("        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {\n");
		javaCode.append("            View view = LayoutInflater.from(parent.getContext())\n");
		javaCode.append("                    .inflate(R.layout.").append(itemLayoutName).append(", parent, false);\n");
		javaCode.append("            return new ViewHolder(view);\n");
		javaCode.append("        }\n\n");
		
		javaCode.append("        @Override\n");
		javaCode.append("        public void onBindViewHolder(ViewHolder holder, int position) {\n");
		javaCode.append("            ").append(itemClassName).append(" item = itemList.get(position);\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> widget : itemWidgets.entrySet()) {
				String widgetId = widget.getKey();
				String widgetType = widget.getValue();
				
				String fieldName = toCamelCase(widgetId);
				
				if (widgetType.equals("TextView") || widgetType.equals("Button") ||
				widgetType.equals("EditText") || widgetType.equals("CheckBox")) {
					javaCode.append("            holder.").append(fieldName)
					.append(".setText(item.get").append(capitalize(fieldName)).append("());\n");
				} else if (widgetType.equals("ImageView") || widgetType.equals("ImageButton")) {
					javaCode.append("            holder.").append(fieldName)
					.append(".setImageResource(item.get").append(capitalize(fieldName)).append("());\n");
				} else if (widgetType.equals("RatingBar")) {
					javaCode.append("            holder.").append(fieldName)
					.append(".setRating(item.get").append(capitalize(fieldName)).append("());\n");
				} else if (widgetType.equals("ProgressBar")) {
					javaCode.append("            holder.").append(fieldName)
					.append(".setProgress(item.get").append(capitalize(fieldName)).append("());\n");
				}
			}
		}
		javaCode.append("        }\n\n");
		
		javaCode.append("        @Override\n");
		javaCode.append("        public int getItemCount() {\n");
		javaCode.append("            return itemList.size();\n");
		javaCode.append("        }\n\n");
		
		javaCode.append("        public class ViewHolder extends RecyclerView.ViewHolder {\n\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> widget : itemWidgets.entrySet()) {
				String widgetId = widget.getKey();
				String widgetType = widget.getValue();
				String fieldName = toCamelCase(widgetId);
				
				javaCode.append("            private ").append(widgetType)
				.append(" ").append(fieldName).append(";\n");
			}
			javaCode.append("\n");
		}
		
		javaCode.append("            public ViewHolder(View itemView) {\n");
		javaCode.append("                super(itemView);\n\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> widget : itemWidgets.entrySet()) {
				String widgetId = widget.getKey();
				String fieldName = toCamelCase(widgetId);
				
				javaCode.append("                ").append(fieldName)
				.append(" = itemView.findViewById(R.id.").append(widgetId).append(");\n");
			}
			
			javaCode.append("\n                itemView.setOnClickListener(new View.OnClickListener() {\n");
			javaCode.append("                    @Override\n");
			javaCode.append("                    public void onClick(View v) {\n");
			javaCode.append("                        int position = getAdapterPosition();\n");
			javaCode.append("                        if (position != RecyclerView.NO_POSITION) {\n");
			javaCode.append("                            // Handle item click\n");
			javaCode.append("                        }\n");
			javaCode.append("                    }\n");
			javaCode.append("                });\n");
		}
		javaCode.append("            }\n");
		javaCode.append("        }\n");
		javaCode.append("    }\n\n");
		
		generateItemModelClass(javaCode, itemClassName, itemWidgets);
	}
	
	private static void generateItemModelClass(StringBuilder javaCode,
	String itemClassName,
	Map<String, String> itemWidgets) {
		
		javaCode.append("    /*\n");
		javaCode.append("     * AUTO GENERATED BY BlackLogics\n");
		javaCode.append("     * Model class for ").append(itemClassName).append("\n");
		javaCode.append("     */\n");
		javaCode.append("    private class ").append(itemClassName).append(" {\n\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> widget : itemWidgets.entrySet()) {
				String widgetId = widget.getKey();
				String widgetType = widget.getValue();
				String fieldName = toCamelCase(widgetId);
				String javaType = mapWidgetToJavaType(widgetType);
				
				javaCode.append("        private ").append(javaType).append(" ").append(fieldName).append(";\n");
			}
			javaCode.append("\n");
			
			javaCode.append("        public ").append(itemClassName).append("() {\n");
			javaCode.append("        }\n\n");
			
			for (Map.Entry<String, String> widget : itemWidgets.entrySet()) {
				String widgetId = widget.getKey();
				String widgetType = widget.getValue();
				String fieldName = toCamelCase(widgetId);
				String javaType = mapWidgetToJavaType(widgetType);
				String capitalized = capitalize(fieldName);
				
				javaCode.append("        public ").append(javaType).append(" get").append(capitalized).append("() {\n");
				javaCode.append("            return ").append(fieldName).append(";\n");
				javaCode.append("        }\n\n");
				
				javaCode.append("        public void set").append(capitalized).append("(").append(javaType).append(" ").append(fieldName).append(") {\n");
				javaCode.append("            this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
				javaCode.append("        }\n\n");
			}
		}
		javaCode.append("    }\n\n");
	}
	
	private static String toCamelCase(String snakeCase) {
		if (snakeCase == null || snakeCase.isEmpty()) return snakeCase;
		
		StringBuilder result = new StringBuilder();
		String[] parts = snakeCase.split("_");
		result.append(parts[0]);
		
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].length() > 0) {
				result.append(parts[i].substring(0, 1).toUpperCase())
				.append(parts[i].substring(1));
			}
		}
		return result.toString();
	}
	
	private static String capitalize(String str) {
		if (str == null || str.isEmpty()) return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	private static String mapWidgetToJavaType(String widgetType) {
		if (widgetType == null) return "String";
		
		switch (widgetType) {
			case "TextView":
			case "EditText":
			case "Button":
			case "CheckBox":
			case "RadioButton":
			case "Switch":
			case "ToggleButton":
			return "String";
			
			case "ImageView":
			case "ImageButton":
			return "int";
			
			case "RatingBar":
			return "float";
			
			case "ProgressBar":
			case "SeekBar":
			return "int";
			
			default:
			return "String";
		}
	}
	
	private static String generateCustomViewAdapterFields(String widgetId,
	String widgetType, String customViewName) {
		if (widgetType == null || widgetType.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		String adapterClass = cvCapitalize(widgetId) + "Adapter";
		String listName     = widgetId + "List";
		String itemType = "HashMap<String, Object>";
		
		if ("ListView".equals(widgetType) || "GridView".equals(widgetType)
		|| "ExpandableListView".equals(widgetType) || "Spinner".equals(widgetType)) {
			sb.append("    private ").append(adapterClass)
			.append(" ").append(widgetId).append("Adapter;\n");
			sb.append("    private ArrayList<").append(itemType).append("> ")
			.append(listName).append(" = new ArrayList<").append(itemType).append(">();\n");
		} else if ("RecyclerView".equals(widgetType)) {
			sb.append("    private ").append(adapterClass)
			.append(" ").append(widgetId).append("Adapter;\n");
			sb.append("    private ArrayList<").append(itemType).append("> ")
			.append(listName).append(" = new ArrayList<").append(itemType).append(">();\n");
			sb.append("    private RecyclerView.LayoutManager ")
			.append(widgetId).append("LayoutManager;\n");
		}
		return sb.toString();
	}
	
	private static String generateCustomViewAdapterInit(String widgetId,
	String widgetType, String customViewName, String activityName, String layoutPath) {
		StringBuilder initOut = new StringBuilder();
		StringBuilder classOut = new StringBuilder();
		generateCustomViewAdapterInit(widgetId, widgetType, customViewName, activityName, layoutPath, initOut, classOut);
		return initOut.toString() + classOut.toString();
	}
	
	private static void generateCustomViewAdapterInit(String widgetId,
	String widgetType, String customViewName, String activityName, String layoutPath,
	StringBuilder sb, StringBuilder classOut) {
		if (widgetType == null || widgetType.isEmpty()) return;
		String adapterClass = cvCapitalize(widgetId) + "Adapter";
		String listName     = widgetId + "List";
		String layoutName   = cvToSnakeCase(customViewName);
		String itemType = "HashMap<String, Object>";
		
		if ("ListView".equals(widgetType) || "GridView".equals(widgetType)) {
			sb.append("        ").append(listName)
			.append(" = new ArrayList<").append(itemType).append(">();\n");
			sb.append("        ").append(widgetId).append("Adapter = new ")
			.append(adapterClass).append("(").append(listName).append(");\n");
			sb.append("        ").append(widgetId)
			.append(".setAdapter(").append(widgetId).append("Adapter);\n");
			sb.append("        ").append(widgetId)
			.append(".setOnItemClickListener(new AdapterView.OnItemClickListener() {\n");
			sb.append("            @Override\n");
			sb.append("            public void onItemClick(AdapterView<?> _adView, View _v, int _position, long _id) {\n");
			String clickLogic = generateWidgetListenerCode(activityName, widgetId, "onItemClick");
			if (clickLogic != null && !clickLogic.isEmpty()) {
				sb.append("                ").append(clickLogic.replace("\n", "\n                ")).append("\n");
			}
			sb.append("            }\n        });\n\n");
			classOut.append(buildListAdapterClass(adapterClass, customViewName, layoutName, widgetId, activityName, layoutPath));
			
		} else if ("Spinner".equals(widgetType)) {
			sb.append("        ").append(listName)
			.append(" = new ArrayList<").append(itemType).append(">();\n");
			sb.append("        ").append(widgetId).append("Adapter = new ")
			.append(adapterClass).append("(").append(listName).append(");\n");
			sb.append("        ").append(widgetId)
			.append(".setAdapter(").append(widgetId).append("Adapter);\n");
			sb.append("        ").append(widgetId)
			.append(".setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {\n");
			sb.append("            @Override\n");
			sb.append("            public void onItemSelected(AdapterView<?> _adView, View _v, int _position, long _id) {\n");
			String selLogic = generateWidgetListenerCode(activityName, widgetId, "onItemSelected");
			if (selLogic != null && !selLogic.isEmpty()) {
				sb.append("                ").append(selLogic.replace("\n", "\n                ")).append("\n");
			}
			sb.append("            }\n");
			sb.append("            @Override\n");
			sb.append("            public void onNothingSelected(AdapterView<?> _adView) {}\n");
			sb.append("        });\n\n");
			classOut.append(buildSpinnerAdapterClass(adapterClass, customViewName, layoutName, widgetId, activityName, layoutPath));
			
		} else if ("RecyclerView".equals(widgetType)) {
			sb.append("        ").append(listName)
			.append(" = new ArrayList<").append(itemType).append(">();\n");
			sb.append("        ").append(widgetId).append("LayoutManager = new LinearLayoutManager(this);\n");
			sb.append("        ").append(widgetId)
			.append(".setLayoutManager(").append(widgetId).append("LayoutManager);\n");
			sb.append("        ").append(widgetId).append("Adapter = new ")
			.append(adapterClass).append("(").append(listName).append(");\n");
			sb.append("        ").append(widgetId)
			.append(".setAdapter(").append(widgetId).append("Adapter);\n");
			sb.append("        ").append(widgetId).append(".setHasFixedSize(true);\n\n");
			classOut.append(buildRecyclerAdapterClass(adapterClass, customViewName, layoutName, widgetId, activityName, layoutPath));
			
		} else if ("ExpandableListView".equals(widgetType)) {
			sb.append("        ").append(listName)
			.append(" = new ArrayList<").append(itemType).append(">();\n");
			sb.append("        ").append(widgetId).append("Adapter = new ")
			.append(adapterClass).append("(").append(listName).append(");\n");
			sb.append("        ").append(widgetId)
			.append(".setAdapter(").append(widgetId).append("Adapter);\n");
			sb.append("        ").append(widgetId)
			.append(".setOnChildClickListener(new ExpandableListView.OnChildClickListener() {\n");
			sb.append("            @Override\n");
			sb.append("            public boolean onChildClick(ExpandableListView _elv, View _v, int _groupPos, int _childPos, long _id) {\n");
			String childLogic = generateCodeFromBlocks(activityName, widgetId + "_onChildClick");
			if (childLogic != null && !childLogic.isEmpty()) {
				sb.append("                ").append(childLogic.replace("\n", "\n                ")).append("\n");
			}
			sb.append("                return false;\n            }\n        });\n\n");
			classOut.append(buildListAdapterClass(adapterClass, customViewName, layoutName, widgetId, activityName, layoutPath));
		}
	}
	private static String buildListAdapterClass(String adapterClass, String customViewName, String layoutName) {
		return buildListAdapterClass(adapterClass, customViewName, layoutName, null, null, null);
	}
	
	private static String buildListAdapterClass(String adapterClass, String customViewName, String layoutName,
	String widgetId, String activityName, String layoutPath) {
		StringBuilder sb = new StringBuilder();
		String itemType = "HashMap<String, Object>";
		sb.append("\n    public class ").append(adapterClass).append(" extends BaseAdapter {\n\n");
		sb.append("        ArrayList<").append(itemType).append("> _data;\n\n");
		sb.append("        public ").append(adapterClass)
		.append("(ArrayList<").append(itemType).append("> _arr) {\n");
		sb.append("            _data = _arr;\n        }\n\n");
		sb.append("        @Override\n        public int getCount() { return _data.size(); }\n\n");
		sb.append("        @Override\n        public ").append(itemType).append(" getItem(int _index) { return _data.get(_index); }\n\n");
		sb.append("        @Override\n        public long getItemId(int _index) { return _index; }\n\n");
		sb.append("        @Override\n");
		sb.append("        public View getView(final int _position, View _v, ViewGroup _container) {\n");
		sb.append("            LayoutInflater _inflater = getLayoutInflater();\n");
		sb.append("            View _view = _v;\n");
		sb.append("            if (_view == null) {\n");
		sb.append("                _view = _inflater.inflate(R.layout.").append(layoutName).append(", null);\n");
		sb.append("            }\n\n");
		
		Map<String, String> itemWidgets = (layoutPath != null)
		? extractWidgetsFromItemLayout(layoutPath, layoutName)
		: null;
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> w : itemWidgets.entrySet()) {
				sb.append("            final ").append(w.getValue()).append(" ").append(w.getKey())
				.append(" = _view.findViewById(R.id.").append(w.getKey()).append(");\n");
			}
			sb.append("\n");
		}
		
		String bindLogic = (widgetId != null && activityName != null)
		? generateWidgetListenerCode(activityName, widgetId, "onCustomListBind")
		: null;
		if (bindLogic != null && !bindLogic.trim().isEmpty()) {
			sb.append("            ").append(bindLogic.replace("\n", "\n            ")).append("\n");
		}
		
		sb.append("            return _view;\n        }\n    }\n\n");
		return sb.toString();
	}
	
	private static String buildRecyclerAdapterClass(String adapterClass, String customViewName,
	String layoutName, String widgetId, String activityName, String layoutPath) {
		StringBuilder sb = new StringBuilder();
		String vh = adapterClass + "VH";
		String itemType = "HashMap<String, Object>";
		sb.append("\n    public class ").append(adapterClass)
		.append(" extends RecyclerView.Adapter<").append(adapterClass).append(".").append(vh).append("> {\n\n");
		sb.append("        ArrayList<").append(itemType).append("> _data;\n\n");
		sb.append("        public ").append(adapterClass)
		.append("(ArrayList<").append(itemType).append("> _arr) {\n");
		sb.append("            _data = _arr;\n        }\n\n");
		sb.append("        @Override\n");
		sb.append("        public ").append(vh).append(" onCreateViewHolder(ViewGroup parent, int viewType) {\n");
		sb.append("            LayoutInflater _inflater = getLayoutInflater();\n");
		sb.append("            View _v = _inflater.inflate(R.layout.").append(layoutName).append(", parent, false);\n");
		sb.append("            return new ").append(vh).append("(_v);\n        }\n\n");
		
		Map<String, String> itemWidgets = (layoutPath != null)
		? extractWidgetsFromItemLayout(layoutPath, layoutName)
		: null;
		
		sb.append("        @Override\n");
		sb.append("        public void onBindViewHolder(final ").append(vh).append(" holder, final int position) {\n");
		sb.append("            final ").append(itemType).append(" item = _data.get(position);\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> w : itemWidgets.entrySet()) {
				sb.append("            ").append(w.getValue()).append(" ").append(w.getKey())
				.append(" = holder.").append(w.getKey()).append(";\n");
			}
		}
		
		String bindLogic = generateWidgetListenerCode(activityName, widgetId, "onCustomListBind");
		if (bindLogic != null && !bindLogic.trim().isEmpty()) {
			sb.append("            ").append(bindLogic.replace("\n", "\n            ")).append("\n");
		}
		sb.append("        }\n\n");
		
		sb.append("        @Override\n");
		sb.append("        public int getItemCount() { return _data.size(); }\n\n");
		sb.append("        public class ").append(vh).append(" extends RecyclerView.ViewHolder {\n\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> w : itemWidgets.entrySet()) {
				sb.append("            public ").append(w.getValue()).append(" ").append(w.getKey()).append(";\n");
			}
			sb.append("\n");
		}
		
		sb.append("            public ").append(vh).append("(final View itemView) {\n");
		sb.append("                super(itemView);\n");
		
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> w : itemWidgets.entrySet()) {
				sb.append("                ").append(w.getKey())
				.append(" = itemView.findViewById(R.id.").append(w.getKey()).append(");\n");
			}
		}
		
		String itemClickLogic = generateWidgetListenerCode(activityName, widgetId, "onItemClick");
		sb.append("                itemView.setOnClickListener(new View.OnClickListener() {\n");
		sb.append("                    @Override\n");
		sb.append("                    public void onClick(View v) {\n");
		sb.append("                        final int _position = getAdapterPosition();\n");
		sb.append("                        if (_position != RecyclerView.NO_POSITION) {\n");
		if (itemClickLogic != null && !itemClickLogic.trim().isEmpty()) {
			sb.append("                            ").append(itemClickLogic.replace("\n", "\n                            ")).append("\n");
		}
		sb.append("                        }\n");
		sb.append("                    }\n                });\n");
		
		String itemLongClickLogic = generateWidgetListenerCode(activityName, widgetId, "onItemLongClick");
		if (itemLongClickLogic != null && !itemLongClickLogic.trim().isEmpty()) {
			sb.append("                itemView.setOnLongClickListener(new View.OnLongClickListener() {\n");
			sb.append("                    @Override\n");
			sb.append("                    public boolean onLongClick(View v) {\n");
			sb.append("                        final int _position = getAdapterPosition();\n");
			sb.append("                        if (_position != RecyclerView.NO_POSITION) {\n");
			sb.append("                            ").append(itemLongClickLogic.replace("\n", "\n                            ")).append("\n");
			sb.append("                        }\n");
			sb.append("                        return true;\n");
			sb.append("                    }\n                });\n");
		}
		
		sb.append("            }\n        }\n    }\n\n");
		return sb.toString();
	}
	private static String cvToSnakeCase(String name) {
		if (name == null || name.isEmpty()) return "custom_view";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c) && i > 0) sb.append('_');
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}
	
	private static String cvCapitalize(String s) {
		if (s == null || s.isEmpty()) return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
	
	private static String prettyIndent(String code, String indent) {
		if (code == null || code.isEmpty()) return "";
		String[] lines = code.trim().split("\n", -1);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lines.length; i++) {
			sb.append(indent).append(lines[i]);
			if (i < lines.length - 1) sb.append("\n");
		}
		return sb.toString();
	}
	
	private static final String INDENT_UNIT = "    ";
	
	public static String formatJavaCode(String code) {
		if (code == null || code.isEmpty()) return code;
		
		char[] chars = code.toCharArray();
		int len = chars.length;
		StringBuilder out = new StringBuilder(len + 512);
		
		int indentLevel = 0;
		boolean inLineComment = false;
		boolean inBlockComment = false;
		boolean inString = false;
		boolean inChar = false;
		boolean escapeNext = false;
		
		for (int i = 0; i < len; i++) {
			char c = chars[i];
			
			if (inLineComment) {
				out.append(c);
				if (c == '\n') {
					inLineComment = false;
					appendIndent(out, indentLevel);
				}
				continue;
			}
			
			if (inBlockComment) {
				out.append(c);
				if (c == '*' && i + 1 < len && chars[i + 1] == '/') {
					out.append(chars[++i]);
					inBlockComment = false;
				}
				continue;
			}
			
			if (escapeNext) {
				out.append(c);
				escapeNext = false;
				continue;
			}
			
			if (inChar || inString) {
				out.append(c);
				if (c == '\\') {
					escapeNext = true;
				} else if (inChar && c == '\'') {
					inChar = false;
				} else if (inString && c == '"') {
					inString = false;
				}
				continue;
			}
			
			if (c == '/' && i + 1 < len && chars[i + 1] == '/') {
				out.append(c).append(chars[++i]);
				inLineComment = true;
				continue;
			}
			if (c == '/' && i + 1 < len && chars[i + 1] == '*') {
				out.append(c).append(chars[++i]);
				inBlockComment = true;
				continue;
			}
			if (c == '\'') {
				inChar = true;
				out.append(c);
				continue;
			}
			if (c == '"') {
				inString = true;
				out.append(c);
				continue;
			}
			
			if (c == '\n') {
				
				trimTrailingSpaces(out);
				out.append(c);
				appendIndent(out, indentLevel);
				continue;
			}
			
			if (c == '{') {
				indentLevel++;
				out.append(c);
				continue;
			}
			
			if (c == '}') {
				indentLevel = Math.max(0, indentLevel - 1);
				
				trimOneIndentUnitIfLineIsBlank(out);
				out.append(c);
				continue;
			}
			
			out.append(c);
		}
		
		return out.toString();
	}
	
	private static void appendIndent(StringBuilder sb, int level) {
		for (int i = 0; i < level; i++) sb.append(INDENT_UNIT);
	}
	
	private static void trimTrailingSpaces(StringBuilder sb) {
		int end = sb.length();
		while (end > 0 && sb.charAt(end - 1) == ' ') end--;
		sb.delete(end, sb.length());
	}
	private static String buildSpinnerAdapterClass(String adapterClass, String customViewName, String layoutName,
	String widgetId, String activityName, String layoutPath) {
		StringBuilder sb = new StringBuilder();
		String itemType = "HashMap<String, Object>";
		sb.append("\n    public class ").append(adapterClass).append(" extends BaseAdapter {\n\n");
		sb.append("        ArrayList<").append(itemType).append("> _data;\n\n");
		sb.append("        public ").append(adapterClass)
		.append("(ArrayList<").append(itemType).append("> _arr) {\n");
		sb.append("            _data = _arr;\n        }\n\n");
		sb.append("        @Override\n        public int getCount() { return _data.size(); }\n\n");
		sb.append("        @Override\n        public ").append(itemType).append(" getItem(int _index) { return _data.get(_index); }\n\n");
		sb.append("        @Override\n        public long getItemId(int _index) { return _index; }\n\n");
		sb.append("        @Override\n");
		sb.append("        public View getView(final int _position, View _v, ViewGroup _container) {\n");
		sb.append("            return buildView(_position, _v, _container);\n        }\n\n");
		sb.append("        @Override\n");
		sb.append("        public View getDropDownView(final int _position, View _v, ViewGroup _container) {\n");
		sb.append("            return buildView(_position, _v, _container);\n        }\n\n");
		sb.append("        private View buildView(final int _position, View _v, ViewGroup _container) {\n");
		sb.append("            LayoutInflater _inflater = getLayoutInflater();\n");
		sb.append("            View _view = _v;\n");
		sb.append("            if (_view == null) {\n");
		sb.append("                _view = _inflater.inflate(R.layout.").append(layoutName).append(", null);\n");
		sb.append("            }\n\n");
		
		Map<String, String> itemWidgets = (layoutPath != null)
		? extractWidgetsFromItemLayout(layoutPath, layoutName)
		: null;
		if (itemWidgets != null && !itemWidgets.isEmpty()) {
			for (Map.Entry<String, String> w : itemWidgets.entrySet()) {
				sb.append("            final ").append(w.getValue()).append(" ").append(w.getKey())
				.append(" = _view.findViewById(R.id.").append(w.getKey()).append(");\n");
			}
			sb.append("\n");
		}
		
		String bindLogic = (widgetId != null && activityName != null)
		? generateWidgetListenerCode(activityName, widgetId, "onCustomListBind")
		: null;
		if (bindLogic != null && !bindLogic.trim().isEmpty()) {
			sb.append("            ").append(bindLogic.replace("\n", "\n            ")).append("\n");
		}
		
		sb.append("            return _view;\n        }\n    }\n\n");
		return sb.toString();
	}
	private static void trimOneIndentUnitIfLineIsBlank(StringBuilder sb) {
		
		int i = sb.length();
		int lineStart = i;
		while (lineStart > 0 && sb.charAt(lineStart - 1) != '\n') lineStart--;
		
		boolean onlySpaces = true;
		for (int j = lineStart; j < i; j++) {
			if (sb.charAt(j) != ' ') { onlySpaces = false; break; }
		}
		if (onlySpaces && (i - lineStart) >= INDENT_UNIT.length()) {
			sb.delete(i - INDENT_UNIT.length(), i);
		}
	}
	
	private static String getTypeNameFromCode(int typeCode) {
		switch (typeCode) {
			case 0: return "boolean";
			case 1: return "double";
			case 2: return "String";
			case 3: return "HashMap<String, Object>";
			case 4: return "ArrayList<Double>";
			case 5: return "ArrayList<String>";
			case 6: return "ArrayList<HashMap<String, Object>>";
			default: return "Object";
		}
	}
	
	
	private static void generateFragmentAdapterClasses(String activityName, StringBuilder javaCode) {
		List<HashMap<String, String>> components = loadComponentsUnified(activityName);
		if (components == null) return;
		for (HashMap<String, String> comp : components) {
			String _cn = comp.get("componentName");
			if (_cn == null) continue;
			if (!"FragmentAdapter".equals(_cn) && !"CustomComponent_22".equals(_cn)) continue;
			String name = comp.get("fieldName");
			if (name == null || name.isEmpty()) continue;
			String cls = name.substring(0, 1).toUpperCase() + (name.length() > 1 ? name.substring(1) : "");
			String getCountLogic = loadComponentEventLogic(activityName, name, "getCount");
			String getItemLogic = loadComponentEventLogic(activityName, name, "getItem");
			String getPageTitleLogic = loadComponentEventLogic(activityName, name, "getPageTitle");
			
			javaCode.append("\n    public class ").append(cls).append("FragmentAdapter extends FragmentStatePagerAdapter {\n");
			javaCode.append("        public ").append(cls).append("FragmentAdapter(FragmentManager fm) {\n");
			javaCode.append("            super(fm);\n");
			javaCode.append("        }\n\n");
			javaCode.append("        @Override\n");
			javaCode.append("        public int getCount() {\n");
			if (getCountLogic != null && !getCountLogic.trim().isEmpty()) {
				javaCode.append("            ").append(getCountLogic.replace("\n", "\n            ")).append("\n");
			} else {
				javaCode.append("            return 0;\n");
			}
			javaCode.append("        }\n\n");
			javaCode.append("        @Override\n");
			javaCode.append("        public Fragment getItem(int _position) {\n");
			if (getItemLogic != null && !getItemLogic.trim().isEmpty()) {
				javaCode.append("            ").append(getItemLogic.replace("\n", "\n            ")).append("\n");
			} else {
				javaCode.append("            return new Fragment();\n");
			}
			javaCode.append("        }\n\n");
			javaCode.append("        @Override\n");
			javaCode.append("        public CharSequence getPageTitle(int _position) {\n");
			if (getPageTitleLogic != null && !getPageTitleLogic.trim().isEmpty()) {
				javaCode.append("            ").append(getPageTitleLogic.replace("\n", "\n            ")).append("\n");
			} else {
				javaCode.append("            return null;\n");
			}
			javaCode.append("        }\n");
			javaCode.append("    }\n");
		}
	}
	
	private static String loadComponentEventLogic(String activityName, String componentId, String eventName) {
		if (activityName == null || componentId == null || eventName == null) return "";
		
		try {
			String logic = ProjectLogicRepository.loadComponentEventLogic(activityName, componentId, eventName);
			if (logic != null && !logic.trim().isEmpty()) return logic;
		} catch (Throwable ignored) {}
		
		String act = activityName;
		if (act.endsWith(".java")) act = act.substring(0, act.length() - 5);
		String[] keys = new String[] {
			componentId + "_" + eventName + "_" + act,
			componentId + "_" + eventName,
			eventName + "_" + componentId + "_" + act,
			componentId + "_" + eventName + " %d.position_" + act,
			componentId + "_" + eventName + " %d.position",
			componentId + "_" + eventName + " %d._position_" + act,
			componentId + "_" + eventName + " %d._position"
		};
		for (String key : keys) {
			try {
				String logic = generateCodeFromBlocks(activityName, key.contains("_" + act) ? key.substring(0, key.lastIndexOf("_" + act)) : key);
			} catch (Throwable ignored) {}
		}
		String[] eventKeys = new String[] {
			componentId + "_" + eventName,
			componentId + "_" + eventName + " %d.position",
			componentId + "_" + eventName + " %d._position",
			eventName + " " + componentId
		};
		for (String ek : eventKeys) {
			try {
				String logic = generateCodeFromBlocks(activityName, ek);
				if (logic != null && !logic.trim().isEmpty()) return logic;
			} catch (Throwable ignored) {}
		}
		
		try {
			java.lang.reflect.Method listM = BlockStorage.class.getMethod("listKeys");
			Object keysObj = listM.invoke(null);
			if (keysObj instanceof java.util.Collection) {
				for (Object k : (java.util.Collection) keysObj) {
					String ks = String.valueOf(k);
					if (ks.contains(componentId) && ks.contains(eventName)) {
						ArrayList blocks = BlockStorage.load(ks);
						if (blocks != null && !blocks.isEmpty()) {
							hb generator = new hb(eventName, new es(), activityName, blocks);
							String code = generator.a();
							if (code != null && !code.trim().isEmpty()) return code;
						}
					}
				}
			}
		} catch (Throwable ignored) {}
		
		return "";
	}
	
}
