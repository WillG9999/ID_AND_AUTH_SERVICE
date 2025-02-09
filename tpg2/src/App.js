import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';



function App() {
  return (
    <div className="App">
      
      <Stack direction="horizontal" gap={2}>
  <Button as="a" variant="primary">
    Button as link
  </Button>
  <Button as="a" variant="success">
    Button as link
  </Button>
</Stack>;
    
      


      
    </div>
  );
}

export default App;
