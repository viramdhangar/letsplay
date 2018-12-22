package girnarsoft.com.demoapp.fragment;

import android.databinding.ViewDataBinding;

import java.util.List;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.activity.adapter.MatchRecyclerAdapter;
import girnarsoft.com.demoapp.activity.model.MatchModel;
import girnarsoft.com.demoapp.activity.service.IMatchService;
import girnarsoft.com.demoapp.activity.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.databinding.FragmentCompletedMatchBinding;
import girnarsoft.com.demoapp.network.viewcallback.AbstractViewCallback;
import girnarsoft.com.demoapp.utils.CommonUtility;
import girnarsoft.com.demoapp.utils.PacksConstants;
import girnarsoft.com.demoapp.utils.ValidationUtils;


public class CompletedMatchFragment extends AbstractBaseFragment {
    public static final String TAG = "MatchListFragment";
    private FragmentCompletedMatchBinding binding;
    private MatchRecyclerAdapter adapter;
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_completed_match;
    }

    @Override
    protected void createView(ViewDataBinding viewDataBinding) {
        binding = (FragmentCompletedMatchBinding)viewDataBinding;
        binding.setCompletedMatchFragment(this);
    }

    @Override
    protected void onFragmentReady() {
        super.onFragmentReady();
        fetchMatchesList();
    }

    private void setAdapter(List<MatchModel> matchModelList) {
        if (ValidationUtils.isListNotEmpty(matchModelList)) {
            adapter = new MatchRecyclerAdapter(getContext(), matchModelList);
            CommonUtility.setRecyclerBasicProperties(getContext(), binding.recyclerViewMatchList, false);
            binding.recyclerViewMatchList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener((view, viewModel) -> {
                MatchModel myTaskModel = (MatchModel) viewModel;
            });

        } else {
            // No data Available
        }
    }
    private void fetchMatchesList(){
        IMatchService service = getAbstractBaseActivity().getLocator().getService(IMatchService.class);
        if (null != service) {
            service.getMatchList(getContext(),PacksConstants.MatchType.NOT_STARTED , new AbstractViewCallback<MatchViewModel>() {

                @Override
                public void onSuccess(MatchViewModel matchViewModel) {
                    setAdapter(matchViewModel.getMatchModelList());
                }

                @Override
                public boolean isLive() {
                    return false;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

}
