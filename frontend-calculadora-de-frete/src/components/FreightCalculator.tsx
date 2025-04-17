import { useState } from 'react';
import axios from 'axios';
import { API_FULL_URL } from '../config/api';

export default function FreightCalculator() {
  const [cep, setCep] = useState('');
  const [freightCost, setFreightCost] = useState<number | null>(null);
  const [loading, setLoading] = useState(false);
  const isValidCep = (cep: string) => /^\d{8}$/.test(cep.replace(/\D/g, ''));

  const handleCalculate = async () => {
    const cleanedCep = cep.replace(/\D/g, '');
    if (!isValidCep(cleanedCep)) {
      alert('Por favor, insira um CEP válido com 8 números.');
      return;
    }

    setLoading(true);
    try {
      const response = await axios.post(`${API_FULL_URL}/calcular`, {
        cep: cep.replace(/\D/g, '')
      });
      setFreightCost(response.data.freightCost);
    } catch (error) {
      let errorMessage = "Erro ao calcular frete";

      if (axios.isAxiosError(error)) {
        errorMessage = error.response?.data?.message || error.message;
      } else if (error instanceof Error) {
        errorMessage = error.message;
      }

      alert(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="hero-container">
      <h1>Calculadora de Frete</h1>

      <div className="input-group">
        <label>CEP:</label>
        <input
          type="text"
          value={cep}
          onChange={(e) => setCep(e.target.value)}
          placeholder="Ex: 01001000"
        />
      </div>

      <div className="button-group">
        <button onClick={handleCalculate} disabled={loading}>
          {loading ? 'Calculando...' : 'Calcular Frete'}
        </button>
      </div>

      {freightCost !== null && (
        <div className="result">
          <strong>Valor do Frete: R$ {freightCost.toFixed(2)}</strong>
        </div>
      )}
    </div>
  );
}
