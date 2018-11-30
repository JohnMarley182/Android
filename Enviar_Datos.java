package johnmarley182.checkforfails;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Enviar_Datos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Enviar_Datos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Enviar_Datos extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText equipo, numero, posicion, falla;
    Button insertar;
    ProgressDialog progreso;

    View vista;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public Enviar_Datos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Enviar_Datos.
     */
    // TODO: Rename and change types and number of parameters
    public static Enviar_Datos newInstance(String param1, String param2) {
        Enviar_Datos fragment = new Enviar_Datos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        vista = inflater.inflate(R.layout.fragment_enviar__datos, container, false);
        equipo=(EditText) vista.findViewById(R.id.txEquipo);
        numero=(EditText) vista.findViewById(R.id.txNumero);
        posicion=(EditText) vista.findViewById(R.id.txPosicion);
        falla=(EditText) vista.findViewById(R.id.txDescripcion);

        insertar = (Button) vista.findViewById(R.id.btnEnviar);

        request = Volley.newRequestQueue(getContext());

        insertar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CargarWebService();
            }
        });

        return vista;
    }

    private void CargarWebService()
    {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Enviando...");
        progreso.show();

        String url ="https://johnmarley182.000webhostapp.com/Insertar.php?equipo="+equipo.getText().toString()+
                "&n_economico="+numero.getText()+"&posicion="+posicion.getText().toString()+
                "&falla="+falla.getText().toString();

        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response)
    {
        Toast.makeText(getContext(), "Se Han Enviado los Datos Correctamente", Toast.LENGTH_LONG).show();
        progreso.hide();
        equipo.setText("");
        numero.setText("");
        posicion.setText("");
        falla.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        progreso.hide();
        Toast.makeText(getContext(), "No Se Pudo Enviar Los Datos   " + error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
