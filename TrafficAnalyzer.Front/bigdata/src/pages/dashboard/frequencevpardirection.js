import PropTypes from 'prop-types';
import { useState, useEffect } from 'react';

// material-ui
import { useTheme } from '@mui/material/styles';

// third-party
import ReactApexChart from 'react-apexcharts';

// chart options
const areaChartOptions = {
    chart: {
        height: 450,
        type: 'area',
        toolbar: {
            show: false
        }
    },
    dataLabels: {
        enabled: false
    },
    stroke: {
        curve: 'smooth',
        width: 2
    },
    grid: {
        strokeDashArray: 0,
    }
};


const FrequenceVparDirection = ({ slot }) => {
    const theme = useTheme();

    const { primary, secondary } = theme.palette.text;
    const line = theme.palette.divider;

    const [options, setOptions] = useState(areaChartOptions);

    useEffect(() => {
        setOptions((prevState) => ({
            ...prevState,
            colors: [theme.palette.primary.main, theme.palette.primary[700]],
            xaxis: {
                categories:
                    slot === 'day'
                        ? ['07','08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '00']
                        : ['03', '06','09','12','15','18','20'],
                labels: {
                    style: {
                        colors: [
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary,
                            secondary
                        ]
                    }
                },
                axisBorder: {
                    show: true,
                    color: line,                },
                    tickAmount: slot === 'day' ? 24 : 50
            }
            ,
            yaxis: {
                labels: {
                    style: {
                        colors: [secondary]
                    }
                }
            },
            grid: {
                borderColor: line
            },
            tooltip: {
                theme: 'light'
            }
        }));
    }, [primary, secondary, secondary, line, theme, slot]);

    const [series, setSeries] = useState([
        {
            name: 'Entrées',
            data: [24]
        },
        {
            name: 'Sorties',
            data: [24]
        },
       
    ]);

    useEffect(() => {
        setSeries([
            {
                name: 'Entrées',
                data: slot === 'day' ? [24361,30745, 24224,16298,17404,21831,22186,17300,18378,22553,25557,24796,18817,12269,8022,6150,4732,1477] : [4155,20418,13077,26299,13721,19721,5144]
            },
            {
                name: 'Sorties',
                data: slot === 'day' ? [15527,20331,16984,13465,15911,21808,19554,16166,18895,27128,31837,30266,21019,13583,7592,5822,4103,1429] : [5344,20102,12062,24426,12529,17884,3385]
            },
            
        ]);
    }, [slot]);

    return <ReactApexChart options={options} series={series} type="area" height={450} />;
};

FrequenceVparDirection.propTypes = {
    slot: PropTypes.string
};

export default FrequenceVparDirection;
