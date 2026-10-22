package com.nexusteam.blacklogics.generator.source;

import com.nexusteam.blacklogics.generator.source.model.ActivityConfig;
import com.nexusteam.blacklogics.generator.source.model.ActivityType;
import com.nexusteam.blacklogics.generator.source.model.ActivityTypeRegistry;
import com.nexusteam.blacklogics.generator.source.model.DialogConfig;
import com.nexusteam.blacklogics.editor.layout.LayoutLoader;

import java.util.Map;

/**
 * Production Fragment / DialogFragment / BottomSheetDialogFragment code generation.
 *
 * NO STUBS. Reuses real logic already in JavaCodeBuilder
 * (addFieldsSection, addInitializeMethod, addWidgetEventBindings, chrome, adapters)
 * and only emits the Fragment-specific lifecycle shell + dialog overrides.
 *
 * Call order from SourceCodeGenerator when activityType.isFragment():
 *   1. addFragmentImports
 *   2. JavaCodeBuilder.addFieldsSection          (already emits rootView for fragments)
 *   3. addOnCreateViewAndOnViewCreated          (this class)
 *   4. JavaCodeBuilder.addInitializeMethod      (already uses rootView.findViewById)
 *   5. JavaCodeBuilder.addInitializeLogicMethod
 *   6. addFragmentLifecycleOverrides            (this class)
 *   7. addFragmentDialogOverrides               (this class – dialog types only)
 *   8. JavaCodeBuilder.addCustomFunctions / addMoreBlocksCode / addUtilityMethods
 */
public final class FragmentCodeBuilder {

	private FragmentCodeBuilder() {}

	// =====================================================================
	// Imports
	// =====================================================================

	public static void addFragmentImports(StringBuilder javaCode, ActivityType type) {
		if (type == null || !type.isFragment()) return;

		appendIfMissing(javaCode, "import android.os.Bundle;\n");
		appendIfMissing(javaCode, "import android.view.LayoutInflater;\n");
		appendIfMissing(javaCode, "import android.view.View;\n");
		appendIfMissing(javaCode, "import android.view.ViewGroup;\n");
		appendIfMissing(javaCode, "import android.content.Context;\n");
		appendIfMissing(javaCode, "import androidx.annotation.NonNull;\n");
		appendIfMissing(javaCode, "import androidx.annotation.Nullable;\n");
		appendIfMissing(javaCode, "import androidx.fragment.app.Fragment;\n");

		if (type.isDialogFragment()) {
			appendIfMissing(javaCode, "import android.app.Dialog;\n");
			appendIfMissing(javaCode, "import android.view.Window;\n");
			appendIfMissing(javaCode, "import android.view.WindowManager;\n");
			appendIfMissing(javaCode, "import androidx.appcompat.app.AlertDialog;\n");

			if (type == ActivityType.BOTTOM_SHEET_DIALOG_FRAGMENT) {
				appendIfMissing(javaCode,
					"import com.google.android.material.bottomsheet.BottomSheetDialog;\n");
				appendIfMissing(javaCode,
					"import com.google.android.material.bottomsheet.BottomSheetDialogFragment;\n");
			} else if (type == ActivityType.APP_COMPAT_DIALOG_FRAGMENT) {
				appendIfMissing(javaCode,
					"import androidx.appcompat.app.AppCompatDialogFragment;\n");
			} else {
				appendIfMissing(javaCode,
					"import androidx.fragment.app.DialogFragment;\n");
			}
		}
		if (type == ActivityType.LIST_FRAGMENT) {
			appendIfMissing(javaCode, "import androidx.fragment.app.ListFragment;\n");
		}
		if (type == ActivityType.PREFERENCE_FRAGMENT) {
			appendIfMissing(javaCode,
				"import androidx.preference.PreferenceFragmentCompat;\n");
		}
	}

	private static void appendIfMissing(StringBuilder sb, String line) {
		if (!sb.toString().contains(line.trim())) {
			sb.append(line);
		}
	}

	// =====================================================================
	// onCreateView + onViewCreated
	// =====================================================================

	/**
	 * Emits onCreateView + onViewCreated.
	 * Skips onCreateView for ListFragment / PreferenceFragmentCompat.
	 * initialize() is still emitted by JavaCodeBuilder.addInitializeMethod
	 * which already uses rootView.findViewById when isFragment==true.
	 */
	public static void addOnCreateViewAndOnViewCreated(StringBuilder javaCode,
			String activityName, String layoutName, ActivityType type) {

		String resName = sanitizeLayoutName(layoutName);

		if (type == ActivityType.LIST_FRAGMENT
				|| type == ActivityType.PREFERENCE_FRAGMENT) {
			javaCode.append("    // ListFragment / PreferenceFragmentCompat manage their own view.\n");
			javaCode.append("    // onCreateView is intentionally not overridden.\n\n");

			javaCode.append("    @Override\n");
			javaCode.append("    public void onViewCreated(@NonNull View view,\n");
			javaCode.append("                              @Nullable Bundle savedInstanceState) {\n");
			javaCode.append("        super.onViewCreated(view, savedInstanceState);\n");
			javaCode.append("        rootView = view;\n");
			javaCode.append("        initialize(savedInstanceState);\n");
			javaCode.append("        initializeLogic();\n");
			javaCode.append("    }\n\n");
			return;
		}

		javaCode.append("    @Override\n");
		javaCode.append("    public View onCreateView(@NonNull LayoutInflater inflater,\n");
		javaCode.append("                             @Nullable ViewGroup container,\n");
		javaCode.append("                             @Nullable Bundle savedInstanceState) {\n");
		javaCode.append("        rootView = inflater.inflate(R.layout.")
				.append(resName).append(", container, false);\n");
		javaCode.append("        return rootView;\n");
		javaCode.append("    }\n\n");

		javaCode.append("    @Override\n");
		javaCode.append("    public void onViewCreated(@NonNull View view,\n");
		javaCode.append("                              @Nullable Bundle savedInstanceState) {\n");
		javaCode.append("        super.onViewCreated(view, savedInstanceState);\n");
		javaCode.append("        initialize(savedInstanceState);\n");
		javaCode.append("        initializeLogic();\n");
		javaCode.append("    }\n\n");
	}

	private static String sanitizeLayoutName(String layoutName) {
		if (layoutName == null || layoutName.trim().isEmpty()) return "fragment_main";
		String n = layoutName.trim();
		if (n.endsWith(".xml")) n = n.substring(0, n.length() - 4);
		return n;
	}

	// =====================================================================
	// Fragment lifecycle (correct signatures + onDestroyView nulling)
	// =====================================================================

	public static void addFragmentLifecycleOverrides(StringBuilder javaCode,
			String activityName, String projectPath, String layoutPath,
			ActivityType type) {

		emitIfHasLogic(javaCode, activityName, "onAttach",
			"    @Override\n" +
			"    public void onAttach(@NonNull Context context) {\n" +
			"        super.onAttach(context);\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onCreate",
			"    @Override\n" +
			"    public void onCreate(@Nullable Bundle savedInstanceState) {\n" +
			"        super.onCreate(savedInstanceState);\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onStart",
			"    @Override\n" +
			"    public void onStart() {\n" +
			"        super.onStart();\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onResume",
			"    @Override\n" +
			"    public void onResume() {\n" +
			"        super.onResume();\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onPause",
			"    @Override\n" +
			"    public void onPause() {\n" +
			"        super.onPause();\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onStop",
			"    @Override\n" +
			"    public void onStop() {\n" +
			"        super.onStop();\n",
			"    }\n\n");

		// onDestroyView – ALWAYS emit (null fields even with no user logic)
		{
			String body = safeSystemEvent(activityName, "onDestroyView");
			javaCode.append("    @Override\n");
			javaCode.append("    public void onDestroyView() {\n");
			javaCode.append("        super.onDestroyView();\n");
			if (body != null && !body.trim().isEmpty()) {
				javaCode.append("        ").append(body.replace("\n", "\n        ")).append("\n");
			}
			Map<String, String> widgets =
					LayoutLoader.extractWidgetsFromLayout(activityName, layoutPath);
			if (widgets != null) {
				for (String id : widgets.keySet()) {
					if (id != null && !id.isEmpty()) {
						javaCode.append("        ").append(id).append(" = null;\n");
					}
				}
			}
			javaCode.append("        rootView = null;\n");
			javaCode.append("    }\n\n");
		}

		emitIfHasLogic(javaCode, activityName, "onDestroy",
			"    @Override\n" +
			"    public void onDestroy() {\n" +
			"        super.onDestroy();\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onDetach",
			"    @Override\n" +
			"    public void onDetach() {\n" +
			"        super.onDetach();\n",
			"    }\n\n");

		emitIfHasLogic(javaCode, activityName, "onSaveInstanceState",
			"    @Override\n" +
			"    public void onSaveInstanceState(@NonNull Bundle outState) {\n" +
			"        super.onSaveInstanceState(outState);\n",
			"    }\n\n");

		{
			String body = safeSystemEvent(activityName, "onActivityResult");
			if (body != null && !body.trim().isEmpty()) {
				javaCode.append("    @Override\n");
				javaCode.append("    @SuppressWarnings(\"deprecation\")\n");
				javaCode.append("    public void onActivityResult(int requestCode, int resultCode, @Nullable android.content.Intent data) {\n");
				javaCode.append("        super.onActivityResult(requestCode, resultCode, data);\n");
				javaCode.append("        ").append(body.replace("\n", "\n        ")).append("\n");
				javaCode.append("    }\n\n");
			}
		}

		{
			String body = safeSystemEvent(activityName, "onRequestPermissionsResult");
			if (body != null && !body.trim().isEmpty()) {
				javaCode.append("    @Override\n");
				javaCode.append("    public void onRequestPermissionsResult(int requestCode,\n");
				javaCode.append("            @NonNull String[] permissions, @NonNull int[] grantResults) {\n");
				javaCode.append("        super.onRequestPermissionsResult(requestCode, permissions, grantResults);\n");
				javaCode.append("        ").append(body.replace("\n", "\n        ")).append("\n");
				javaCode.append("    }\n\n");
			}
		}
	}

	private static void emitIfHasLogic(StringBuilder javaCode, String activityName,
			String event, String header, String footer) {
		String body = safeSystemEvent(activityName, event);
		if (body == null || body.trim().isEmpty()) return;
		javaCode.append(header);
		javaCode.append("        ").append(body.replace("\n", "\n        ")).append("\n");
		javaCode.append(footer);
	}

	/**
	 * Uses the package-visible helper added by JavaCodeBuilder_PATCHES.
	 * Falls back to reflection so generation never crashes.
	 */
	private static String safeSystemEvent(String activityName, String eventName) {
		try {
			return JavaCodeBuilder.generateSystemEventCodePublic(activityName, eventName);
		} catch (Throwable t1) {
			try {
				java.lang.reflect.Method m = JavaCodeBuilder.class.getDeclaredMethod(
						"generateSystemEventCode", String.class, String.class);
				m.setAccessible(true);
				Object r = m.invoke(null, activityName, eventName);
				return r != null ? (String) r : "";
			} catch (Throwable t2) {
				return "";
			}
		}
	}

	// =====================================================================
	// Dialog / BottomSheet overrides
	// =====================================================================

	public static void addFragmentDialogOverrides(StringBuilder javaCode,
			String activityName, ActivityType type) {
		if (type == null || !type.isDialogFragment()) return;

		ActivityConfig config = ActivityTypeRegistry.getInstance().getActivityConfig(activityName);
		DialogConfig dc = (config != null) ? config.getDialogConfig() : null;

		if (dc != null && dc.getTitle() != null && !dc.getTitle().isEmpty()) {
			javaCode.append("    @NonNull\n");
			javaCode.append("    @Override\n");
			javaCode.append("    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {\n");
			javaCode.append("        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()");
			if (dc.getThemeResId() != 0) {
				javaCode.append(", ").append(dc.getThemeResId());
			}
			javaCode.append(");\n");
			javaCode.append("        builder.setTitle(\"")
					.append(escapeJava(dc.getTitle())).append("\");\n");
			javaCode.append("        builder.setCancelable(").append(dc.isCancelable()).append(");\n");
			javaCode.append("        // Custom layout still comes from onCreateView\n");
			javaCode.append("        return builder.create();\n");
			javaCode.append("    }\n\n");
		}

		if (dc != null && (dc.getWidthRatio() != null || dc.getHeightRatio() != null || dc.isFullScreen())) {
			javaCode.append("    @Override\n");
			javaCode.append("    public void onStart() {\n");
			javaCode.append("        super.onStart();\n");
			javaCode.append("        Dialog dialog = getDialog();\n");
			javaCode.append("        if (dialog != null && dialog.getWindow() != null) {\n");
			javaCode.append("            Window window = dialog.getWindow();\n");
			if (dc.isFullScreen()) {
				javaCode.append("            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,\n");
				javaCode.append("                    ViewGroup.LayoutParams.MATCH_PARENT);\n");
			} else {
				javaCode.append("            int width = ViewGroup.LayoutParams.MATCH_PARENT;\n");
				javaCode.append("            int height = ViewGroup.LayoutParams.WRAP_CONTENT;\n");
				if (dc.getWidthRatio() != null) {
					javaCode.append("            width = (int) (requireContext().getResources()\n");
					javaCode.append("                    .getDisplayMetrics().widthPixels * ")
							.append(dc.getWidthRatio()).append("f);\n");
				}
				if (dc.getHeightRatio() != null) {
					javaCode.append("            height = (int) (requireContext().getResources()\n");
					javaCode.append("                    .getDisplayMetrics().heightPixels * ")
							.append(dc.getHeightRatio()).append("f);\n");
				}
				javaCode.append("            window.setLayout(width, height);\n");
			}
			javaCode.append("        }\n");
			javaCode.append("    }\n\n");
		}

		javaCode.append("    public void _dismissDialog() {\n");
		javaCode.append("        if (isAdded()) {\n");
		javaCode.append("            dismissAllowingStateLoss();\n");
		javaCode.append("        }\n");
		javaCode.append("    }\n\n");
	}

	private static String escapeJava(String s) {
		if (s == null) return "";
		return s.replace("\\", "\\\\").replace("\"", "\\\"");
	}
}
