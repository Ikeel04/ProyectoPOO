public class PrincipalPerro{
	public static void main(String[] args){
		Perro miPerro = new Perro();
		System.out.println(miPerro.toString());
		
		miPerro.setNombre("Laika");
		miPerro.setEdad(6);
		miPerro.setRaza("Labrador");
		miPerro.setColor("negra");
		System.out.println(miPerro.toString());
		System.out.println(miPerro.ladrar());
		
		Perro perroMajo = new Perro();
		System.out.println(perroMajo.toString());
		
		perroMajo.setNombre("Toby");
		perroMajo.setEdad(4);
		perroMajo.setRaza("Shih tzu");
		perroMajo.setColor("Blanco");
		
		System.out.println(perroMajo.toString());
		System.out.println(perroMajo.ladrar());
	}
}