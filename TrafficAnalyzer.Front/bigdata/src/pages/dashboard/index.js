import { useState } from 'react';
import ReportVitesseParCategorieAreaChart from './vitesseparcategorie';
// material-ui
import {
    Avatar,
    AvatarGroup,
    Box,
    Button,
    Grid,
    List,
    ListItemAvatar,
    ListItemButton,
    ListItemSecondaryAction,
    ListItemText,
    MenuItem,
    Stack,
    TextField,
    Typography
} from '@mui/material';
// project import
import FrequenceVparDirection from './frequencevpardirection';
import Map from './map';
import MainCard from 'components/MainCard';
import CategorieParVehicles from './categorieparvehicles';
// assets
import logo from 'assets/images/users/logo.png';
// avatar style
const avatarSX = {
    width: 36,
    height: 36,
    fontSize: '1rem'
};

// action style
const actionSX = {
    mt: 0.75,
    ml: 1,
    top: 'auto',
    right: 'auto',
    alignSelf: 'flex-start',
    transform: 'none'
};

// sales report status
const status = [
    
];

// ==============================|| DASHBOARD - DEFAULT ||============================== //

const DashboardDefault = () => {
    const [value, setValue] = useState('today');
    const [slot, setSlot] = useState('week');

    return (
        <Grid container rowSpacing={4.5} columnSpacing={2.75}>
            {/* row 1 */}
            <Grid item xs={12} sx={{ mb: -2.25 }}>
                <img src={logo} width="200px" height="200px"/>
            </Grid>
            
            {/* row 2 */}
            <Grid item xs={12} md={6} lg={8}>
                <Grid container alignItems="center" justifyContent="space-between">
                    <Grid item>
                        <Typography variant="h5">Entrées/Sorties de toutes les véhicules par rapport à l'heure ou la journée </Typography>
                    </Grid>
                    <Grid item>
                        <Stack direction="row" alignItems="center" spacing={0}>
                            <Button
                                size="small"
                                onClick={() => setSlot('day')}
                                color={slot === 'day' ? 'primary' : 'secondary'}
                                variant={slot === 'day' ? 'outlined' : 'text'}
                            >
                                Heure
                            </Button>
                            <Button
                                size="small"
                                onClick={() => setSlot('week')}
                                color={slot === 'week' ? 'primary' : 'secondary'}
                                variant={slot === 'week' ? 'outlined' : 'text'}
                            >
                                Jour
                            </Button>
                        </Stack>
                    </Grid>
                </Grid>
                <MainCard content={false} sx={{ mt: 1.5 }}>
                    <Box sx={{ pt: 1, pr: 2 }}>
                        <FrequenceVparDirection slot={slot} />
                    </Box>
                </MainCard>
            </Grid>
            <Grid item xs={12} md={6} lg={4}>
                <Grid container alignItems="center" justifyContent="space-between">
                    <Grid item>
                        <Typography variant="h5">Vitesse moyenne des véhicules par catégorie</Typography>
                    </Grid>
                    <Grid item />
                </Grid>
                <MainCard sx={{ mt: 2 }} content={false}>
                    <Box sx={{ p: 3, pb: 0 }}>
                        <Stack spacing={2}>

                            <Typography variant="h3"></Typography>
                        </Stack>
                    </Box>
                    <ReportVitesseParCategorieAreaChart />
                </MainCard>
            </Grid>

            {/* row 3 */}
            <Grid item xs={12} md={6} lg={8}>
               

                <Grid container alignItems="center" justifyContent="space-between">
                    <Grid item>
                        <Typography variant="h5">Informations par capteurs</Typography>
                    </Grid>
                    <Grid item />
                </Grid>
                <MainCard sx={{ mt: 2 }} content={false}>
                    <Map />
                </MainCard>
            </Grid>
            <Grid item xs={12} md={6} lg={4}>
                <Grid container alignItems="center" justifyContent="space-between">
                    <Grid item>
                        {/* <Typography variant="h5">Analytics Report</Typography> */}
                    </Grid>
                    <Grid item />
                </Grid>
                <MainCard sx={{ mt: 2 }} content={false}>
                    
                    {/* <VehicleDirectionChart /> */}
                </MainCard>
            </Grid>

            {/* row 4 */}
            <Grid item xs={12} md={7} lg={8}>
                 <Grid container alignItems="center" justifyContent="space-between">
                    <Grid item>
                        <Typography variant="h5">Categorie Par direction</Typography>
                    </Grid>
                    <Grid item>
                        
                    </Grid>
                </Grid>
                <MainCard sx={{ mt: 1.75 }}>
                    <Stack spacing={1.5} sx={{ mb: -12 }}>
                        <Typography variant="h6" color="secondary">
                            Direction
                        </Typography>
                    </Stack>
                    <CategorieParVehicles />
                </MainCard>
            </Grid>
            <Grid item xs={12} md={5} lg={4}>
                <Grid container alignItems="center" justifyContent="space-between">
                    <Grid item>
                    </Grid>
                    <Grid item />
                </Grid>

            </Grid>
        </Grid>
    );
};

export default DashboardDefault;
