import { useState } from 'react';
import axios, { AxiosResponse } from 'axios';
import { API_FULL_URL } from '../config/api';

type FreightResponse = {
  freightCost: number;
};

export default function FreightCalculator() {
  const [cep, setCep] = useState('');
  const [freightCost, setFreightCost] = useState<number | null>(null);
  const [loading, setLoading] = useState(false);

  const isValidCep = (cep: string) => /^\d{8}$/.test(cep.replace(/\D/g, ''));

  const handleCalculate = async (): Promise<void> => {
    const cleanedCep = cep.replace(/\D/g, '');
    if (!isValidCep(cleanedCep)) {
      alert('Por favor, insira um CEP válido com 8 números.');
      return;
    }

    setLoading(true);

    try {
      const requests: Array<Promise<AxiosResponse<FreightResponse>>> = API_FULL_URL.map(
        (url: string) => axios.post<FreightResponse>(url, { cep: cleanedCep })
      );

      const response = await Promise.any(requests);
      setFreightCost(response.data.freightCost);
    } catch (error: unknown) {
      let errorMessage = 'Erro ao calcular frete';

      if (error instanceof AggregateError) {
        errorMessage = 'Tente novamente mais tarde (urls).';
      } else if (axios.isAxiosError(error)) {
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
