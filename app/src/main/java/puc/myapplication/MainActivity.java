package puc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import puc.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.seekProntidao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textPercentual.setText(getString(R.string.percentual_prontidao, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Nenhuma ação necessária.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Nenhuma ação necessária.
            }
        });

        binding.btnLimpar.setOnClickListener(view -> limparFormulario());
        binding.btnAcessarDossie.setOnClickListener(view -> validarAcesso());
    }

    private void limparFormulario() {
        binding.editCodinome.setText("");
        binding.editCodigo.setText("");
        binding.editCodinome.setError(null);
        binding.editCodigo.setError(null);
        binding.seekProntidao.setProgress(0);
        binding.editCodinome.requestFocus();
    }

    private void validarAcesso() {
        String codinome = binding.editCodinome.getText().toString().trim();
        String codigo = binding.editCodigo.getText().toString().trim();

        binding.editCodinome.setError(null);
        binding.editCodigo.setError(null);

        if (codinome.length() < 3) {
            binding.editCodinome.setError(getString(R.string.erro_codinome));
            binding.editCodinome.requestFocus();
            return;
        }

        if (!codigo.equals("042")) {
            binding.editCodigo.setError(getString(R.string.erro_codigo));
            binding.editCodigo.requestFocus();
            return;
        }

        if (binding.seekProntidao.getProgress() < 50) {
            Toast.makeText(this, R.string.erro_prontidao, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, DossieActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
