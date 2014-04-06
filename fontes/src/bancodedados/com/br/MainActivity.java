package bancodedados.com.br;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	SQLiteDatabase BancoDeDados = null;
	Cursor cursor;
	Button btSalvar,btBuscar, btProximo, btVoltar, btEditar;
	EditText etLogin, etSenha;
	TextView tvLogin, tvSenha, tvCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CriandoOuAbrindoBD();
        btSalvar = (Button)findViewById(R.id.btSalvar);
        btBuscar = (Button)findViewById(R.id.btBuscar);
        btEditar = (Button)findViewById(R.id.btEditar);
        
        btProximo = (Button)findViewById(R.id.btProximo);
        btVoltar = (Button)findViewById(R.id.btVoltar);
        
        etLogin  = (EditText)findViewById(R.id.etLogin);
        etSenha  = (EditText)findViewById(R.id.etSenha);
        tvCodigo = (TextView)findViewById(R.id.tvCodigo);
        tvLogin  = (TextView)findViewById(R.id.tvLogin);
        tvSenha  = (TextView)findViewById(R.id.tvSenha);
        
        btSalvar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GravarRegistro();
			}
		});

        btEditar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				EditarRegistro();
			}
		});
        
        
        btBuscar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					cursor = BancoDeDados.query(
							"cliente", 
							new String[] {"codigo","login","senha"}, 
							null, 
							null, 
							null, 
							null, 
							null);
					cursor.moveToFirst();
					tvCodigo.setText(cursor.getString(0));
					tvLogin.setText("Login: "+cursor.getString(1));
					tvSenha.setText("Senha: "+cursor.getString(2));
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
        
        btProximo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
		    	try {
					cursor.moveToNext();
					tvCodigo.setText(cursor.getString(0));
					tvLogin.setText("Login: "+cursor.getString(1));
					tvSenha.setText("Senha: "+cursor.getString(2));
				} catch (Exception e) {
		    		Toast.makeText(MainActivity.this, "Você está no final!", 5000).show();
				}
			}
		});
        
        btVoltar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
		    	try {
					cursor.moveToPrevious();
					tvCodigo.setText(cursor.getString(0));
					tvLogin.setText("Login: "+cursor.getString(1));
					tvSenha.setText("Senha: "+cursor.getString(2));
				} catch (Exception e) {
		    		Toast.makeText(MainActivity.this, "Você está no inicio!", 5000).show();
				}
			}
		});
        
    }
    
    public void CriandoOuAbrindoBD(){
    	try {
    		BancoDeDados = openOrCreateDatabase("Banco", MODE_PRIVATE, null);
    		String sql = "CREATE TABLE IF NOT EXISTS cliente(codigo INTEGER PRIMARY KEY, login TEXT, senha TEXT);";
    		BancoDeDados.execSQL(sql);
    		Toast.makeText(MainActivity.this, "Banco Criado!!", 5000).show();
		} catch (Exception e) {
    		Toast.makeText(MainActivity.this, "Erro: "+e.getMessage(), 5000).show();
		}
    }

    
    public void GravarRegistro(){
    	try {
    		String sql = "INSERT INTO cliente (login,senha) values ('"
    				+etLogin.getText().toString()+"','"
    				+etSenha.getText().toString()+"');'";
    		BancoDeDados.execSQL(sql);
    		Toast.makeText(MainActivity.this, "Registro Salvo!!", 5000).show();
			etLogin.setText("");
			etSenha.setText("");
		} catch (Exception e) {
			Toast.makeText(MainActivity.this, "Erro: "+e.getMessage(), 5000).show();
		}
    }

   
    public void EditarRegistro(){
    	try {
    		String sql = "UPDATE cliente SET login = '"
    				+etLogin.getText().toString()+"', Senha = '"
    				+etSenha.getText().toString()+"' where codigo ='"
    				+tvCodigo.getText().toString()+"' ;'";
    		BancoDeDados.execSQL(sql);
    		Toast.makeText(MainActivity.this, "Registro alterado!!", 5000).show();
			etLogin.setText("");
			etSenha.setText("");
		} catch (Exception e) {
			Toast.makeText(MainActivity.this, "Erro: "+e.getMessage(), 35000).show();
		}
    }
   
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
