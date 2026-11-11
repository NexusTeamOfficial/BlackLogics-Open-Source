package com.elfilibustero.quizboard;

import android.content.Context;

import com.elfilibustero.quizboard.beans.QuizBean;
import com.nexusteam.blacklogics.R;

import java.util.ArrayList;

public class QuizList {
    public static ArrayList<QuizBean> quizBean;

    public static ArrayList<QuizBean> getQuizList(Context context) {
        if (quizBean == null) {
            quizBean = new ArrayList<>();
        } else {
            return quizBean;
        }

        quizBean.add(new QuizBean(context.getString(R.string.q_gyroscope_supported), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_index_first_item_one), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_sharedpref_remains), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_sharedpref_deletes_restart), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_events_only_event_tab), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_timer_multiple_tasks), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_firebase_component_tab), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_scroll_multiple_widgets), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_soundpool_any_length), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_drawer_fab_compile_time), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_onstop_after_ondestroy), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_delete_blacklogics_removes_projects), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_blacklogics_autosave), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_sharedpref_fileutil_same), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_update_playstore_diff_key), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_local_project_recover), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_duplicate_id_widgets), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_disable_widgets_enabled_prop), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_alpha_shadow), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_list_only_numbers), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_if_else_one_condition), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_clear_top_removes_uppermost), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_finishactivity_close_app), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_listview_scroll_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_open_other_apps_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_objectanimator_only_way), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_events_auto_added), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_collection_not_shared), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_upload_apk_unsigned), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_delete_collection_items), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_fixed_dp_recommended), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_layout_inside_layout_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_textview_maxlines_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_edittext_disable_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_redirect_website_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_divide_by_zero), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_copy_blocks_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_calendar_calendarview_same), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_one_component_per_activity), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_var_case_same), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_duplicate_list_items_not_possible), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_undo_variable_creation), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_create_widget_duplicate_id), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_lower_version_code), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_oncreate_deletable), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_deleted_project_recover), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_deleted_collection_recover), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_var_activity_a_to_b), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_component_activity_a_to_b), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_custom_blocks_playstore), QuizBean.QUIZ_TYPE_OX));
        quizBean.add(new QuizBean(context.getString(R.string.q_file_blocks_android11), QuizBean.QUIZ_TYPE_OX));

        quizBean.add(new QuizBean(context.getString(R.string.q_filepicker_choose_file), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_finishaffinity_close_app), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_manifest_tags), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_custom_components), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_backup_single_project), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_drawer_view_manager), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_imageview_image_and_bg), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_events_in_property_tab), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_imageview_colorfilter), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_oncreate_first_event), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_invalid_sharedpref_key_crash), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_firebase_storage_upload), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_listview_onbindview), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_fileutil_resize_images), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_statusbar_color_advanced), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_firebase_auth_login), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_overlap_widgets_translation), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_firebase_library_settings), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_imageview_scaletype_ratio), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_custom_list_item), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_forever_without_stop_crash), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_if_then_execute), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_variables_store_types), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_intent_transfer_data), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_custom_item_listview), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_mycollection_shared), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_save_widgets_blocks), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_new_component_blocks_added), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_moreblock_purple), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_change_font_style), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_discord_community), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_onclick_other_widgets), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_events_component_tab), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_change_language_blacklogics), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_edit_image_after_add), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_export_android_studio), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_sign_project_playstore), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_playstore_dev_account), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_center_text_gravity), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_view_java_xml), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_upload_pictures_firebase), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_swap_two_vars_third), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_weight_responsive), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_trim_block), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_number_string_convert), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_calc_screen_width), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_collision_getlocation), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_clear_top_removes_all_previous), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_single_top_reuse), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_startactivity_navigate), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_soundpool_max_stream), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_animate_objectanimator), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_components_own_events), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_dialog_popup), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_timer_may_not_cancel), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_moreblocks_develop_quickly), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_share_projects_without_app), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_verbal_abuse_ban), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_moreblock_in_moreblock), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_custom_hex_color), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_imageview_bg_resource), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_drawer_events_drawer_xml), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_firebase_free_small), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_camera_take_pictures), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_edittext_custom_validator), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_programmatic_styling), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_edittext_hint), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_invisible_takes_space), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_landscape_app), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_infinite_loop_sound), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_algorithms_blocks), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_oncreate_first_lifecycle), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_index_out_of_bounds), QuizBean.QUIZ_TYPE_INFO));
        quizBean.add(new QuizBean(context.getString(R.string.q_list_ordered_by_save), QuizBean.QUIZ_TYPE_INFO));

        quizBean.add(new QuizBean(context.getString(R.string.q_animate_widgets_component), QuizBean.QUIZ_TYPE_OX,
                context.getString(R.string.opt_mediaplayer), context.getString(R.string.opt_objectanimator)));
        quizBean.add(new QuizBean(context.getString(R.string.q_play_longer_sound_component), QuizBean.QUIZ_TYPE_OX,
                context.getString(R.string.opt_soundpool), context.getString(R.string.opt_mediaplayer)));
        quizBean.add(new QuizBean(context.getString(R.string.q_fill_parent_space), QuizBean.QUIZ_TYPE_OX,
                context.getString(R.string.opt_wrap_content), context.getString(R.string.opt_match_parent)));
        quizBean.add(new QuizBean(context.getString(R.string.q_position_children_layout), QuizBean.QUIZ_TYPE_OX,
                context.getString(R.string.opt_layout_gravity), context.getString(R.string.opt_gravity)));
        quizBean.add(new QuizBean(context.getString(R.string.q_spacing_around_widget), QuizBean.QUIZ_TYPE_INFO,
                context.getString(R.string.opt_padding), context.getString(R.string.opt_margin)));
        quizBean.add(new QuizBean(context.getString(R.string.q_change_bg_transparency), QuizBean.QUIZ_TYPE_INFO,
                context.getString(R.string.opt_alpha), context.getString(R.string.opt_bg_color)));
        quizBean.add(new QuizBean(context.getString(R.string.q_number_keyboard_edittext), QuizBean.QUIZ_TYPE_OX,
                context.getString(R.string.opt_ime), context.getString(R.string.opt_numberdecimal)));
        quizBean.add(new QuizBean(context.getString(R.string.q_lifecycle_restart), QuizBean.QUIZ_TYPE_INFO,
                context.getString(R.string.opt_onresume), context.getString(R.string.opt_onstop)));
        quizBean.add(new QuizBean(context.getString(R.string.q_singleline_vs_lines), QuizBean.QUIZ_TYPE_INFO,
                context.getString(R.string.opt_singleline), context.getString(R.string.opt_lines3)));

        return quizBean;
    }
}