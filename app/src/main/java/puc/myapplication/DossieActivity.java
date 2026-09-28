package puc.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import puc.myapplication.databinding.ActivityDossieBinding;

public class DossieActivity extends AppCompatActivity {

    private static final int DEZ_SEGUNDOS_MS = 10_000;
    private static final String SOLUCAO = "Lara";

    private ActivityDossieBinding binding;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDossieBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mediaPlayer = MediaPlayer.create(this, R.raw.transmissao_estacao_17);
        mediaPlayer.setOnCompletionListener(player ->
                binding.textEstadoAudio.setText(R.string.estado_concluido));

        configurarControlesAudio();
        binding.btnVerificarSolucao.setOnClickListener(view -> verificarSolucao());
        binding.btnVoltarCentral.setOnClickListener(view -> finish());
    }

    private void configurarControlesAudio() {
        binding.btnReproduzir.setOnClickListener(view -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                binding.textEstadoAudio.setText(R.string.estado_reproduzindo);
            }
        });

        binding.btnPausar.setOnClickListener(view -> pausarAudio());

        binding.btnInicio.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                mediaPlayer.seekTo(0);
                binding.textEstadoAudio.setText(R.string.estado_parado);
            }
        });

        binding.btnRecuar.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                int novaPosicao = Math.max(0, mediaPlayer.getCurrentPosition() - DEZ_SEGUNDOS_MS);
                mediaPlayer.seekTo(novaPosicao);
            }
        });

        binding.btnAvancar.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                int novaPosicao = Math.min(
                        mediaPlayer.getDuration(),
                        mediaPlayer.getCurrentPosition() + DEZ_SEGUNDOS_MS
                );
                mediaPlayer.seekTo(novaPosicao);
            }
        });
    }

    private void pausarAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            binding.textEstadoAudio.setText(R.string.estado_pausado);
        }
    }

    private void verificarSolucao() {
        String resposta = binding.editResposta.getText().toString().trim();
        String justificativa = binding.editJustificativa.getText().toString().trim();

        binding.editJustificativa.setError(null);

        if (justificativa.length() < 20) {
            binding.editJustificativa.setError(getString(R.string.erro_justificativa));
            binding.editJustificativa.requestFocus();
            return;
        }

        if (resposta.equalsIgnoreCase(SOLUCAO)) {
            binding.textResultado.setText(R.string.resultado_correto);
        } else {
            binding.textResultado.setText(R.string.resultado_incorreto);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausarAudio();
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        binding = null;
        super.onDestroy();
    }
}
