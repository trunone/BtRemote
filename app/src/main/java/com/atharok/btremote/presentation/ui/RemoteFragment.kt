package com.atharok.btremote.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.atharok.btremote.R
import com.atharok.btremote.databinding.FragmentRemoteBinding
import com.atharok.btremote.domain.entities.remoteInput.MouseAction
import com.atharok.btremote.domain.entities.remoteInput.RemoteButtonProperties
import com.atharok.btremote.domain.entities.remoteInput.keyboard.virtualKeyboard.EnglishUSVirtualKeyboardLayout
import com.atharok.btremote.presentation.viewmodel.RemoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RemoteFragment : Fragment() {

    private var _binding: FragmentRemoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RemoteViewModel by viewModel()

    private var lastTouchX = 0f
    private var lastTouchY = 0f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRemoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupTabs()
        setupDpad()
        setupMediaButtons()
        setupMousePad()
        setupKeyboard()
    }

    private fun setupToolbar() {
        binding.toolbar.inflateMenu(R.menu.menu_remote)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_disconnect -> {
                    viewModel.disconnectDevice()
                    findNavController().navigate(R.id.deviceSelectionFragment)
                    true
                }
                R.id.action_settings -> {
                    findNavController().navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        binding.layoutRemoteView.visibility = View.VISIBLE
                        binding.layoutMouseView.visibility = View.GONE
                        binding.layoutKeyboardView.visibility = View.GONE
                    }
                    1 -> {
                        binding.layoutRemoteView.visibility = View.GONE
                        binding.layoutMouseView.visibility = View.VISIBLE
                        binding.layoutKeyboardView.visibility = View.GONE
                    }
                    2 -> {
                        binding.layoutRemoteView.visibility = View.GONE
                        binding.layoutMouseView.visibility = View.GONE
                        binding.layoutKeyboardView.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupDpad() {
        binding.dpadLayout.btnDpadUp.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.MenuUpButton.input)
        }
        binding.dpadLayout.btnDpadDown.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.MenuDownButton.input)
        }
        binding.dpadLayout.btnDpadLeft.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.MenuLeftButton.input)
        }
        binding.dpadLayout.btnDpadRight.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.MenuRightButton.input)
        }
        binding.dpadLayout.btnDpadOk.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.MenuPickButton.input)
        }
        binding.dpadLayout.btnBack.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.BackButton.input)
        }
        binding.dpadLayout.btnHome.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.HomeButton.input)
        }
    }

    private fun setupMediaButtons() {
        binding.mediaLayout.btnPlayPause.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.PlayPauseButton.input)
        }
        binding.mediaLayout.btnPrev.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.RewindButton.input)
        }
        binding.mediaLayout.btnNext.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.ForwardButton.input)
        }
        binding.mediaLayout.btnVolUp.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.VolumeUpButton.input)
        }
        binding.mediaLayout.btnVolDown.setOnClickListener {
            viewModel.sendRemoteReport(RemoteButtonProperties.VolumeDownButton.input)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupMousePad() {
        binding.mousePad.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchX = event.x
                    lastTouchY = event.y
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - lastTouchX
                    val deltaY = event.y - lastTouchY
                    lastTouchX = event.x
                    lastTouchY = event.y
                    viewModel.sendMouseReport(MouseAction.NONE, deltaX, deltaY, 0f)
                    true
                }
                else -> false
            }
        }

        binding.btnMouseLeft.setOnClickListener {
            viewModel.sendMouseReport(MouseAction.MOUSE_CLICK_LEFT, 0f, 0f, 0f)
        }

        binding.btnMouseRight.setOnClickListener {
            viewModel.sendMouseReport(MouseAction.MOUSE_CLICK_RIGHT, 0f, 0f, 0f)
        }
    }

    private fun setupKeyboard() {
        binding.btnSendText.setOnClickListener {
            val text = binding.etInput.text?.toString() ?: ""
            if (text.isNotEmpty()) {
                viewModel.sendTextReport(text, EnglishUSVirtualKeyboardLayout(), true)
                binding.etInput.text?.clear()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}